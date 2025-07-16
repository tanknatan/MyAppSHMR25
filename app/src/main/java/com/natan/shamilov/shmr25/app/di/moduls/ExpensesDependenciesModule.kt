package com.natan.shamilov.shmr25.app.di.moduls

import com.natan.shamilov.shmr25.app.di.AppComponent
import com.natan.shamilov.shmr25.expenses.api.ExpensesDependencies
import dagger.Binds
import dagger.Module

@Module
interface ExpensesDependenciesModule {

    @Binds
    fun bindExpensesDependencies(deps: AppComponent): ExpensesDependencies
}
