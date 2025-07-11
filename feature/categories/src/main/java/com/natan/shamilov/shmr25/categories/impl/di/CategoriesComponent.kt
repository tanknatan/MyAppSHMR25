package com.natan.shamilov.shmr25.categories.impl.di

import com.example.core.di.ViewModelFactoryScope
import com.natan.shamilov.shmr25.categories.api.CategoriesDependencies
import com.natan.shamilov.shmr25.common.impl.di.ViewModelFactory
import dagger.Component

@CategoriesScope
@ViewModelFactoryScope
@Component(
    dependencies = [
        CategoriesDependencies::class
    ],
    modules = [
        CategoriesModule::class,
        CategoriesViewModelModule::class,
        CategoriesRepositoryModule::class
    ]
)
interface CategoriesComponent {

    fun viewModelFactory(): ViewModelFactory

    @Component.Factory
    interface Factory {
        fun create(dependencies: CategoriesDependencies): CategoriesComponent
    }
}
