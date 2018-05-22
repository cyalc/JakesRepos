package com.cyalc.jakesrepos.data.repositories

import android.arch.paging.DataSource
import android.arch.paging.PagedList
import android.arch.paging.RxPagedListBuilder
import com.cyalc.jakesrepos.data.api.GithubApi
import com.cyalc.jakesrepos.data.dao.RepoDao
import com.cyalc.jakesrepos.data.models.NetworkState
import com.cyalc.jakesrepos.data.models.NetworkState.Success
import com.cyalc.jakesrepos.data.models.Repo
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepoRepository
@Inject
constructor(
        private val githubApi: GithubApi,
        private val repoDao: RepoDao
) {

    val networkStateRelay: BehaviorRelay<NetworkState> = BehaviorRelay.createDefault<NetworkState>(Success())

    /**
     * Replace old data with the new one if available
     */
    fun checkRefreshData() {

        repoDao.getAllRepos()
                .filter { it.isNotEmpty() }
                .toObservable()
                .flatMap { _ -> loadReposFromNetwork(1) }
                .take(1)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    repoDao.deleteAllRepos()
                }, {
                    networkStateRelay.accept(NetworkState.Failure("No network connection"))
                })

    }

    /**
     * Get repos from api and save them in db
     * Also send network state to relay
     */
    fun loadAndSaveRepos(page: Int) {
        networkStateRelay.accept(NetworkState.Loading())

        loadReposFromNetwork(page)
                .flatMap<Repo> { it -> Observable.fromIterable(it) }
                .map {
                    it.page = page
                    return@map it
                }
                .toList()
                .doOnSuccess {
                    repoDao.insertRepos(it)
                }
                .subscribeOn(Schedulers.io())
                .subscribe({
                    networkStateRelay.accept(Success())
                }, {
                    networkStateRelay.accept(NetworkState.Failure("No network connection"))
                })
    }

    fun getRepoDataSource(): DataSource.Factory<Int, Repo> {
        return repoDao.load()
    }

    private fun loadReposFromNetwork(page: Int): Observable<List<Repo>> {
        return githubApi.getRepos(page, 15)
    }

    /**
     * Build pagination
     */
    fun getRepoPagedList(): Flowable<PagedList<Repo>> {
        return RxPagedListBuilder(
                getRepoDataSource(),
                PagedList.Config
                        .Builder()
                        .setPageSize(15)
                        .setPrefetchDistance(3)
                        .setEnablePlaceholders(false)
                        .build())
                .setBoundaryCallback(
                        object : PagedList.BoundaryCallback<Repo>() {
                            override fun onZeroItemsLoaded() {
                                super.onZeroItemsLoaded()
                                loadAndSaveRepos(1)
                                Timber.i("Paging first")
                            }

                            override fun onItemAtEndLoaded(itemAtEnd: Repo) {
                                super.onItemAtEndLoaded(itemAtEnd)
                                loadAndSaveRepos(itemAtEnd.page + 1)
                                Timber.i("Paging ${itemAtEnd.page + 1}")
                            }
                        })
                .buildFlowable(BackpressureStrategy.LATEST)
    }
}
