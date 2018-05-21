package com.cyalc.jakesrepos.data.models

import android.arch.persistence.room.Entity
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(
        primaryKeys = ["name"]
)
data class Repo(
        val id: Int,
        val name: String,
        val language: String?,
        @SerializedName("stargazers_count")
        val starCount: Int,
        @SerializedName("forks_count")
        val forkCount: Int,
        @SerializedName("updated_at")
        val updatedAt: Date,
        val description: String?,
        var page: Int
)
