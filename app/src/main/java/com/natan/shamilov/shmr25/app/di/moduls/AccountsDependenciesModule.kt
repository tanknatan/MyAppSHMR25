package com.natan.shamilov.shmr25.app.di.moduls

import com.natan.shamilov.shmr25.account.api.AccountDependencies
import com.natan.shamilov.shmr25.app.di.AppComponent
import dagger.Binds
import dagger.Module

@Module
interface AccountsDependenciesModule {

    @Binds
    fun bindAccountsDependencies(deps: AppComponent): AccountDependencies
}