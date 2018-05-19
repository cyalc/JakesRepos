package com.cyalc.jakesrepos.data.repositories

import com.cyalc.jakesrepos.data.api.GithubApi
import com.cyalc.jakesrepos.data.dao.RepoDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepoRepository
@Inject
constructor(
        val githubApi: GithubApi,
        val repoDao: RepoDao
) {

}
