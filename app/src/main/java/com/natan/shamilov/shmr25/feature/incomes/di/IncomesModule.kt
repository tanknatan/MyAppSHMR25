package com.natan.shamilov.shmr25.feature.incomes.di

import com.natan.shamilov.shmr25.feature.incomes.data.api.IncomesApi
import com.natan.shamilov.shmr25.feature.incomes.data.repository.IncomesRepositoryImpl
import com.natan.shamilov.shmr25.feature.incomes.domain.repository.IncomesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object IncomesModule {

    @Provides
    @Singleton
    fun provideIncomesApi(retrofit: Retrofit): IncomesApi =
        retrofit.create(IncomesApi::class.java)

    @Provides
    @Singleton
    fun provideIncomesRepository(repository: IncomesRepositoryImpl): IncomesRepository = repository
} 