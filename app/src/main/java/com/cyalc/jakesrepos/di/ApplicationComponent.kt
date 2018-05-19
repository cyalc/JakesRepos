package com.cyalc.jakesrepos.di

import com.cyalc.jakesrepos.RepoApp
import com.cyalc.jakesrepos.data.api.DataModule
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, DataModule::class])
interface ApplicationComponent : AndroidInjector<RepoApp> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<RepoApp>()
}
