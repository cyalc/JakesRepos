package com.cyalc.jakesrepos.api

import com.google.gson.annotations.SerializedName
import java.util.*

data class RepoResponse(
        val id: Int,
        val name: String,
        val language: String,
        @SerializedName("stargazers_count")
        val starCount: Int,
        @SerializedName("forks_count")
        val forkCount: Int,
        @SerializedName("updated_at")
        val updatedAt: Date
)
