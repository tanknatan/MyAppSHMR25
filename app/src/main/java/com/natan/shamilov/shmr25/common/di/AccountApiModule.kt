package com.natan.shamilov.shmr25.common.di

import com.natan.shamilov.shmr25.common.data.api.AccountApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
object AccountApiModule {
    @Provides
    @Singleton
    fun provideAccountApi(retrofit: Retrofit): AccountApi =
        retrofit.create(AccountApi::class.java)
} 