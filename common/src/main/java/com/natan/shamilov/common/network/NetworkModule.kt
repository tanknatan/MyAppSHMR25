package com.natan.shamilov.common.network

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object NetworkModule {
    @Provides
    @Singleton
    fun provideNetworkStateProvider(context: Context): NetworkStateProvider =
        NetworkStateProviderImpl(context)
} 