package com.natan.shamilov.shmr25.feature.history.di

import com.natan.shamilov.shmr25.feature.history.data.repository.HistoryRepositoryImpl
import com.natan.shamilov.shmr25.feature.history.domain.repository.HistoryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HistoryModule {
    
    @Binds
    @Singleton
    abstract fun bindHistoryRepository(
        historyRepositoryImpl: HistoryRepositoryImpl
    ): HistoryRepository
} 