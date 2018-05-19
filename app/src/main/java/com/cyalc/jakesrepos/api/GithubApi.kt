package com.cyalc.jakesrepos.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {
    @GET("users/JakeWharton/repos")
    fun getRepos(
            @Query("page") page: Int,
            @Query("per_page") per_page: Int
    ): Observable<List<RepoResponse>>
}
