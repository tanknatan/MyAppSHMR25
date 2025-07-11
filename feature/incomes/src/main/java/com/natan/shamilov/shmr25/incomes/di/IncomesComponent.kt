package com.natan.shamilov.shmr25.incomes.di

import com.example.core.di.ViewModelFactoryScope
import com.natan.shamilov.shmr25.common.impl.di.ViewModelFactory
import com.natan.shamilov.shmr25.common.impl.di.ViewModelFactoryModule
import com.natan.shamilov.shmr25.incomes.api.IncomesDependencies
import dagger.Component

@IncomesScope
@ViewModelFactoryScope
@Component(
    dependencies = [
        IncomesDependencies::class
    ],
    modules = [
        ViewModelFactoryModule::class,
        IncomesViewModelModule::class,
        IncomesRepositoryModule::class
    ]
)
interface IncomesComponent {

    fun viewModelFactory(): ViewModelFactory

    @Component.Factory
    interface Factory {
        fun create(deps: IncomesDependencies): IncomesComponent
    }
}