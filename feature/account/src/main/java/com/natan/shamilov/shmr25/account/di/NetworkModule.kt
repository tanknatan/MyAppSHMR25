package com.natan.shamilov.shmr25.account.di

import com.natan.shamilov.shmr25.feature.account.data.api.AccountApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * Модуль Hilt для предоставления сетевых зависимостей модуля счетов.
 * Конфигурирует и предоставляет API интерфейс для работы со счетами.
 */
@Module
class NetworkModule {

    @Provides
    @AccountsScope
    fun provideAccountsApi(
        retrofit: Retrofit
    ): AccountApi {
        return retrofit.create(AccountApi::class.java)
    }
}