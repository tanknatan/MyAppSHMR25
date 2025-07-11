package com.natan.shamilov.shmr25.app.di.moduls

import com.natan.shamilov.shmr25.app.di.AppComponent
import com.natan.shamilov.shmr25.incomes.api.IncomesDependencies
import dagger.Binds
import dagger.Module

@Module
interface IncomesDependenciesModule {

    @Binds
    fun bindIncomesDependencies(deps: AppComponent): IncomesDependencies
}