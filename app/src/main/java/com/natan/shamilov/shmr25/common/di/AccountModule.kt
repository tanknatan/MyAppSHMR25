package com.natan.shamilov.shmr25.common.di

import com.natan.shamilov.shmr25.common.api.AccountProvider
import com.natan.shamilov.shmr25.common.data.repository.AccountRepositoryImpl
import com.natan.shamilov.shmr25.common.domain.repository.AccountRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AccountModule {
    @Provides
    @Singleton
    fun provideAccountProvider(impl: AccountRepositoryImpl): AccountProvider = impl

    @Provides
    @Singleton
    fun provideAccountRepository(impl: AccountRepositoryImpl): AccountRepository = impl
} 