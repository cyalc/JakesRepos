package com.cyalc.jakesrepos.ui

import android.arch.lifecycle.ViewModel
import com.cyalc.jakesrepos.data.repositories.RepoRepository
import javax.inject.Inject

class RepoViewModel
@Inject
constructor(private val repoRepository: RepoRepository) : ViewModel() {

   val allRepos = repoRepository.getRepoPagedList()
}
