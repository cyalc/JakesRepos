package com.cyalc.jakesrepos.di

import android.app.Application
import com.cyalc.jakesrepos.ui.MainActivity
import com.cyalc.jakesrepos.RepoApp
import com.cyalc.jakesrepos.di.scopes.ActivityScope
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton

@Module(includes = [(AndroidInjectionModule::class)])
abstract class ApplicationModule {

    @Binds
    @Singleton
    abstract fun application(app: RepoApp): Application

    @ActivityScope
    @ContributesAndroidInjector(modules = [(MainActivityModule::class)])
    abstract fun mainActivity(): MainActivity

}
