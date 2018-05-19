package com.cyalc.jakesrepos.di

import android.app.Activity
import android.content.Context
import com.cyalc.jakesrepos.MainActivity
import com.cyalc.jakesrepos.RepoFragment
import com.cyalc.jakesrepos.di.scopes.ActivityScope
import com.cyalc.jakesrepos.di.scopes.FragmentScope
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {

    @Binds
    @ActivityScope
    abstract fun activityContext(activity: Activity): Context

    @Binds
    @ActivityScope
    abstract fun activity(mainActivity: MainActivity): Activity

    @FragmentScope
    @ContributesAndroidInjector(modules = [(RepoFragmentModule::class)])
    abstract fun repoFragment(): RepoFragment
}
