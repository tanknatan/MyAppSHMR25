package com.natan.shamilov.shmr25.common.impl.di

import com.natan.shamilov.shmr25.common.api.CategoriesProvider
import com.natan.shamilov.shmr25.common.impl.data.repository.CategoriesRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class BaseCategoriesModule {
    @Binds
    @ViewModelFactoryScope
    abstract fun bindCategoriesProvider(
        impl: CategoriesRepositoryImpl,
    ): CategoriesProvider
}
