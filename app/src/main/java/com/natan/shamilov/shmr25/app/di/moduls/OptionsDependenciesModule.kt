package com.natan.shamilov.shmr25.app.di.moduls

import com.natan.shamilov.shmr25.app.di.AppComponent
import com.natan.shamilov.shmr25.option.api.OptionsDependencies
import dagger.Binds
import dagger.Module

@Module
interface OptionsDependenciesModule {

    @Binds
    fun bindOptionsDependencies(deps: AppComponent): OptionsDependencies
}