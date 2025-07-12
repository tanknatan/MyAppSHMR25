package com.natan.shamilov.shmr25.app.di.moduls

import com.natan.shamilov.shmr25.app.di.AppComponent
import com.natan.shamilov.shmr25.splash.api.SplashDependencies
import dagger.Binds
import dagger.Module

@Module
interface SplashDependenciesModule {

    @Binds
    fun bindSplashDependencies(deps: AppComponent): SplashDependencies
}
