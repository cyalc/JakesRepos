package com.cyalc.jakesrepos.data.api

import com.cyalc.jakesrepos.data.models.Repo
import io.reactivex.Flowable
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {
    @GET("users/JakeWharton/repos")
    fun getRepos(
            @Query("page") page: Int,
            @Query("per_page") per_page: Int
    ): Observable<List<Repo>>
}
