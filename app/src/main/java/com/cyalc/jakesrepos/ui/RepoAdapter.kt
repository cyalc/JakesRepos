package com.cyalc.jakesrepos.ui

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.view.ViewGroup
import com.cyalc.jakesrepos.data.models.Repo

class RepoAdapter(diffCallback: DiffUtil.ItemCallback<Repo>) : PagedListAdapter<Repo, RepoViewHolder>(diffCallback) {
    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): RepoViewHolder {
        return RepoViewHolder(parent)
    }

    override fun onBindViewHolder(
            holder: RepoViewHolder,
            position: Int
    ) {
        holder.bindTo(getItem(position))
    }
}
