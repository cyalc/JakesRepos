package com.cyalc.jakesrepos

import android.app.Activity
import android.app.Application
import com.cyalc.jakesrepos.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class RepoApp : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    private fun initDagger() {
        DaggerApplicationComponent
                .builder()
                .create(this)
                .inject(this)
    }
}
