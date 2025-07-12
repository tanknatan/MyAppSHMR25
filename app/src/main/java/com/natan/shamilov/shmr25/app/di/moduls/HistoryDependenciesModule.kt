package com.natan.shamilov.shmr25.app.di.moduls

import com.natan.shamilov.shmr25.app.di.AppComponent
import com.natan.shamilov.shmr25.history.api.HistoryDependencies
import dagger.Binds
import dagger.Module

@Module
interface HistoryDependenciesModule {

    @Binds
    fun bindHistoryDependencies(deps: AppComponent): HistoryDependencies
}