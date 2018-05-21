package com.cyalc.jakesrepos.di

import android.support.v4.app.Fragment
import com.cyalc.jakesrepos.ui.RepoFragment
import com.cyalc.jakesrepos.di.scopes.FragmentScope
import dagger.Binds
import dagger.Module

@Module
abstract class RepoFragmentModule {

    @Binds
    @FragmentScope
    abstract fun fragment(repoFragment: RepoFragment): Fragment
}
