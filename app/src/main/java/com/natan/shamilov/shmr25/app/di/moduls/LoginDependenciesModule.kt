package com.natan.shamilov.shmr25.app.di.moduls

import com.natan.shamilov.shmr25.app.di.AppComponent
import com.natan.shamilov.shmr25.login.api.LoginDependencies
import dagger.Binds
import dagger.Module

@Module
interface LoginDependenciesModule {

    @Binds
    fun bindLoginDependencies(deps: AppComponent): LoginDependencies
}