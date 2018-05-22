package com.cyalc.jakesrepos.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.cyalc.jakesrepos.R

class LoadingViewHolder(val parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater
                .from(parent.context)
                .inflate(
                        R.layout.item_loading,
                        parent,
                        false)) {
    fun bindTo() {

    }
}
