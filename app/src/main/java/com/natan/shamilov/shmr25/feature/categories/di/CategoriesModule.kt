package com.natan.shamilov.shmr25.feature.categories.di

import com.natan.shamilov.shmr25.feature.categories.data.api.CategoriesApi
import com.natan.shamilov.shmr25.feature.categories.data.repository.CategoriesRepositoryImpl
import com.natan.shamilov.shmr25.feature.categories.domain.repository.CategoriesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CategoriesModule {

    @Provides
    @Singleton
    fun provideCategoriesApi(retrofit: Retrofit): CategoriesApi =
        retrofit.create(CategoriesApi::class.java)

    @Provides
    @Singleton
    fun provideCategoriesRepository(repository: CategoriesRepositoryImpl): CategoriesRepository = repository
} 