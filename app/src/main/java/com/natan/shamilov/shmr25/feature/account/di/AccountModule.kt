package com.natan.shamilov.shmr25.feature.account.di

import com.natan.shamilov.shmr25.feature.account.data.api.AccountApi
import com.natan.shamilov.shmr25.feature.account.data.repository.AccountRepositoryImpl
import com.natan.shamilov.shmr25.feature.account.domain.repository.AccountRepository
import com.natan.shamilov.shmr25.feature.account.domain.usecase.GetSelectedAccountUseCase
import com.natan.shamilov.shmr25.feature.account.domain.usecase.SetSelectedAccountUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AccountModule {

    @Provides
    @Singleton
    fun provideAccountApi(retrofit: Retrofit): AccountApi =
        retrofit.create(AccountApi::class.java)

    @Provides
    @Singleton
    fun provideAccountRepository(repository: AccountRepositoryImpl): AccountRepository = repository

    @Provides
    @Singleton
    fun provideGetSelectedAccountUseCase(repository: AccountRepository): GetSelectedAccountUseCase =
        GetSelectedAccountUseCase(repository)

    @Provides
    @Singleton
    fun provideSetSelectedAccountUseCase(repository: AccountRepository): SetSelectedAccountUseCase =
        SetSelectedAccountUseCase(repository)
}
