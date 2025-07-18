package com.natan.shamilov.shmr25.common.impl.di

import com.natan.shamilov.shmr25.common.api.SyncPreferencesProvider
import com.natan.shamilov.shmr25.common.impl.data.repository.SyncPreferencesRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface SyncPreferencesRepositoryModule {

    @ViewModelFactoryScope
    @Binds
    fun bindSyncPreferencesRepository(impl: SyncPreferencesRepositoryImpl): SyncPreferencesProvider
}