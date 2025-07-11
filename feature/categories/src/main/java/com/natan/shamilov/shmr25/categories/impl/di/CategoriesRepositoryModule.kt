package com.natan.shamilov.shmr25.categories.impl.di

import com.natan.shamilov.shmr25.feature.categories.data.repository.CategoriesRepositoryImpl
import com.natan.shamilov.shmr25.feature.categories.domain.repository.CategoriesRepository
import dagger.Binds
import dagger.Module

@Module
interface CategoriesRepositoryModule {
    @Binds
    @CategoriesScope
    fun provideCategoriesRepository(repository: CategoriesRepositoryImpl): CategoriesRepository
}