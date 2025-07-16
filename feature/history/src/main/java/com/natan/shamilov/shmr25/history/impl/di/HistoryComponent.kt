package com.natan.shamilov.shmr25.history.impl.di

import com.natan.shamilov.shmr25.common.impl.di.ViewModelFactoryScope
import com.natan.shamilov.shmr25.common.impl.di.ViewModelFactory
import com.natan.shamilov.shmr25.common.impl.di.ViewModelFactoryModule
import com.natan.shamilov.shmr25.history.api.HistoryDependencies
import dagger.Component

@HistoryScope
@ViewModelFactoryScope
@Component(
    dependencies = [
        HistoryDependencies::class
    ],
    modules = [
        ViewModelFactoryModule::class,
        RepositoryModule::class,
        HistoryViewModelModule::class,
        AccountRepositoryModule::class,
        CategoriesRepositoryModule::class
    ]
)
interface HistoryComponent {

    fun viewModelFactory(): ViewModelFactory

    @Component.Factory
    interface Factory {
        fun create(deps: HistoryDependencies): HistoryComponent
    }
}