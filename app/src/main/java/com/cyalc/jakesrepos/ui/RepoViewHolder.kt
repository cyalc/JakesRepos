package com.cyalc.jakesrepos.ui

import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.text.format.DateUtils.FORMAT_ABBREV_RELATIVE
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.cyalc.jakesrepos.R
import com.cyalc.jakesrepos.data.models.Repo

class RepoViewHolder(val parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(
                R.layout.item_repo,
                parent,
                false
        )) {

    private val name = itemView.findViewById<TextView>(R.id.itemRepoName)
    private val description = itemView.findViewById<TextView>(R.id.itemRepoDescription)
    private val language = itemView.findViewById<TextView>(R.id.itemRepoLanguage)
    private val starCount = itemView.findViewById<TextView>(R.id.itemRepoStarCount)
    private val forkCount = itemView.findViewById<TextView>(R.id.itemRepoForkCount)
    private val updateTime = itemView.findViewById<TextView>(R.id.itemRepoUpdateTime)


    fun bindTo(item: Repo?) {
        item?.let {
            name.text = it.name
            description.text = it.description
            starCount.text = it.starCount.toString()
            forkCount.text = it.forkCount.toString()
            updateTime.text = DateUtils.formatDateTime(parent.context, it.updatedAt.time, FORMAT_ABBREV_RELATIVE)
            language.text = it.language
        }


    }
}
