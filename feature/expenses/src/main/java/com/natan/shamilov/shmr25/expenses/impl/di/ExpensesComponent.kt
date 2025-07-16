package com.natan.shamilov.shmr25.expenses.impl.di

import com.natan.shamilov.shmr25.common.impl.di.ViewModelFactoryScope
import com.natan.shamilov.shmr25.common.impl.di.ViewModelFactory
import com.natan.shamilov.shmr25.common.impl.di.ViewModelFactoryModule
import com.natan.shamilov.shmr25.expenses.api.ExpensesDependencies
import dagger.Component

@ExpensesScope
@ViewModelFactoryScope
@Component(
    dependencies = [
        ExpensesDependencies::class
    ],
    modules = [
        ExpensesRepositoryModule::class,
        CategoriesRepositoryModule::class,
        AccountRepositoryModule::class,
        ExpensesViewModelModule::class,
        ViewModelFactoryModule::class
    ]
)
interface ExpensesComponent {

    fun viewModelFactory(): ViewModelFactory

    @Component.Factory
    interface Factory {

        fun create(deps: ExpensesDependencies): ExpensesComponent
    }
}