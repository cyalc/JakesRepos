package com.cyalc.jakesrepos.ui

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.util.DiffUtil
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cyalc.jakesrepos.R
import com.cyalc.jakesrepos.data.models.NetworkState
import com.cyalc.jakesrepos.data.models.Repo
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class RepoFragment : Fragment(), HasSupportFragmentInjector {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>

    private val disposable = CompositeDisposable()

    private lateinit var repoViewModel: RepoViewModel

    private lateinit var repoAdapter: RepoAdapter

    private lateinit var recylerViewRepos: RecyclerView

    private val diffCallback = object : DiffUtil.ItemCallback<Repo>() {
        override fun areItemsTheSame(oldItem: Repo, newItem: Repo) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Repo, newItem: Repo) = oldItem == newItem
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        repoAdapter = RepoAdapter(diffCallback)
        recylerViewRepos.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        recylerViewRepos.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recylerViewRepos.adapter = repoAdapter

        repoViewModel = ViewModelProviders.of(this, viewModelFactory).get(RepoViewModel::class.java)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_repo, container, false)
        recylerViewRepos = view.findViewById(R.id.fragmentRepoRecyclerViewRepos)
        return view
    }

    override fun onStart() {
        super.onStart()

        disposable.addAll(
                repoViewModel.allRepos
                        .subscribe({
                            repoAdapter.submitList(it)
                        })
                ,
                repoViewModel.networkState
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            when (it) {
                                is NetworkState.Success -> {
                                    repoAdapter.hideLoading()
                                }
                                is NetworkState.Failure -> {
                                    repoAdapter.hideLoading()
                                    Snackbar.make(recylerViewRepos, it.message, Snackbar.LENGTH_LONG).show()
                                }
                                is NetworkState.Loading -> {
                                    repoAdapter.showLoading()
                                }
                            }
                        })
        )

        repoViewModel.checkReposAreRefresh()
    }

    override fun onStop() {
        disposable.clear()
        super.onStop()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment>? {
        return childFragmentInjector
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    companion object {
        fun newInstance(): RepoFragment = RepoFragment()
    }
}
