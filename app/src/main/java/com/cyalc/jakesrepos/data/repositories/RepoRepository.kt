package com.cyalc.jakesrepos.data.repositories

import android.arch.paging.DataSource
import android.arch.paging.PagedList
import android.arch.paging.RxPagedListBuilder
import com.cyalc.jakesrepos.data.api.GithubApi
import com.cyalc.jakesrepos.data.models.Repo
import com.cyalc.jakesrepos.data.dao.RepoDao
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

    fun loadAndSaveRepos(page: Int) {
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

                }, {

                })
    }

    fun getRepoDataSource(): DataSource.Factory<Int, Repo> {
        return repoDao.load()
    }

    private fun loadReposFromNetwork(page: Int): Observable<List<Repo>> {
        return githubApi.getRepos(page, 15)
    }


    fun getRepoPagedList(): Flowable<PagedList<Repo>> {
        return RxPagedListBuilder(
                getRepoDataSource(),
                PagedList.Config
                        .Builder()
                        .setPageSize(15)
                        .setPrefetchDistance(3)
                        .setEnablePlaceholders(true)
                        .build())
                .setBoundaryCallback(
                        object : PagedList.BoundaryCallback<Repo>() {
                            override fun onZeroItemsLoaded() {
                                super.onZeroItemsLoaded()
                                loadAndSaveRepos(1)
                            }

                            override fun onItemAtEndLoaded(itemAtEnd: Repo) {
                                super.onItemAtEndLoaded(itemAtEnd)
                                loadAndSaveRepos(itemAtEnd.page + 1)
                            }
                        })
                .buildFlowable(BackpressureStrategy.LATEST)
    }
}
