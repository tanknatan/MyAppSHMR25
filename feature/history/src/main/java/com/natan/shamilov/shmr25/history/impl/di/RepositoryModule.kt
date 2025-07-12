package com.natan.shamilov.shmr25.history.impl.di

import com.natan.shamilov.shmr25.history.impl.data.repository.HistoryRepositoryImpl
import com.natan.shamilov.shmr25.history.impl.domain.repository.HistoryRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    @HistoryScope
    fun bindHistoryRepository(impl: HistoryRepositoryImpl): HistoryRepository
}