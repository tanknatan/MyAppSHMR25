package com.natan.shamilov.shmr25.history.di

import com.example.core.di.ViewModelFactoryScope
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
        HistoryViewModelModule::class
    ]
)
interface HistoryComponent {

    fun viewModelFactory(): ViewModelFactory

    @Component.Factory
    interface Factory {
        fun create(deps: HistoryDependencies): HistoryComponent
    }
}