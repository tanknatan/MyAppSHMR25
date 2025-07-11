package com.natan.shamilov.shmr25.account.di

import com.natan.shamilov.shmr25.feature.account.data.api.AccountApi
import com.natan.shamilov.shmr25.account.data.repository.AccountRepositoryImpl
import com.natan.shamilov.shmr25.account.domain.repository.AccountRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
object AccountModule {

    @Provides
    @Singleton
    fun provideAccountApi(retrofit: Retrofit): AccountApi =
        retrofit.create(AccountApi::class.java)

    @Provides
    @Singleton
    fun provideAccountRepository(repository: AccountRepositoryImpl): AccountRepository = repository
}
