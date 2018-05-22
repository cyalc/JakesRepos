package com.cyalc.jakesrepos.ui

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.cyalc.jakesrepos.R
import com.cyalc.jakesrepos.data.models.Repo

class RepoAdapter(diffCallback: DiffUtil.ItemCallback<Repo>) : PagedListAdapter<Repo, RecyclerView.ViewHolder>(diffCallback) {

    var loading = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_loading -> LoadingViewHolder(parent)
            R.layout.item_repo -> RepoViewHolder(parent)
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_repo -> (holder as RepoViewHolder).bindTo(getItem(position))
            R.layout.item_loading -> (holder as LoadingViewHolder).bindTo()
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (loading) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (loading && position == itemCount - 1) {
            R.layout.item_loading
        } else {
            R.layout.item_repo
        }
    }

    fun showLoading() {
        loading = true
        notifyItemInserted(itemCount)
    }

    fun hideLoading() {
        loading = false
        notifyItemRemoved(itemCount)
    }
}
