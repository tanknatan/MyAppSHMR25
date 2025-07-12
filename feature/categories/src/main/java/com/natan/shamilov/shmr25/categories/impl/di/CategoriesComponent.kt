package com.natan.shamilov.shmr25.categories.impl.di

import com.natan.shamilov.shmr25.common.impl.di.ViewModelFactoryScope
import com.natan.shamilov.shmr25.categories.api.CategoriesDependencies
import com.natan.shamilov.shmr25.common.impl.di.ViewModelFactory
import com.natan.shamilov.shmr25.common.impl.di.ViewModelFactoryModule
import dagger.Component

@CategoriesScope
@ViewModelFactoryScope
@Component(
    dependencies = [
        CategoriesDependencies::class
    ],
    modules = [
        ViewModelFactoryModule::class,
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
