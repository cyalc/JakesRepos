package com.cyalc.jakesrepos.di

import com.cyalc.jakesrepos.RepoApp
import com.cyalc.jakesrepos.api.NetworkModule
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, NetworkModule::class])
interface ApplicationComponent : AndroidInjector<RepoApp> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<RepoApp>()
}
