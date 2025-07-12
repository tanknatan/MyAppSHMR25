package com.natan.shamilov.shmr25.common.impl.di

import com.natan.shamilov.shmr25.common.api.NetworkChekerProvider
import com.natan.shamilov.shmr25.common.impl.data.repository.NetworkRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface NetworkModule {
    /**
     * Предоставляет приёмник состояния сети.
     * Используется для отслеживания состояния подключения к интернету.
     */
    @Binds
    @Singleton
    fun bindNetworkRepository(
        networkRepositoryImpl: NetworkRepositoryImpl,
    ): NetworkChekerProvider
}
