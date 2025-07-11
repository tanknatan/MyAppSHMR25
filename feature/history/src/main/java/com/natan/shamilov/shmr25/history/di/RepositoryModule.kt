package com.natan.shamilov.shmr25.history.di

import com.natan.shamilov.shmr25.feature.history.data.repository.HistoryRepositoryImpl
import com.natan.shamilov.shmr25.feature.history.domain.repository.HistoryRepository
import com.natan.shamilov.shmr25.history.di.HistoryScope
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    @HistoryScope
    fun bindHistoryRepository(impl: HistoryRepositoryImpl): HistoryRepository
}