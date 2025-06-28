package com.natan.shamilov.shmr25.feature.expenses.di

import com.natan.shamilov.shmr25.feature.expenses.data.api.ExpensesApi
import com.natan.shamilov.shmr25.feature.expenses.data.repository.ExpensesRepositoryImpl
import com.natan.shamilov.shmr25.feature.expenses.domain.repository.ExpensesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ExpensesModule {

    @Provides
    @Singleton
    fun provideExpensesApi(retrofit: Retrofit): ExpensesApi =
        retrofit.create(ExpensesApi::class.java)

    @Provides
    @Singleton
    fun provideExpensesRepository(repository: ExpensesRepositoryImpl): ExpensesRepository = repository
} 