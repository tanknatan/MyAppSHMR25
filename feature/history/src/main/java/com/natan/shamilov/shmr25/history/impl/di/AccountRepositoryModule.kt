package com.natan.shamilov.shmr25.history.impl.di

import com.natan.shamilov.shmr25.history.impl.data.repository.AccountRepositoryImpl
import com.natan.shamilov.shmr25.history.impl.domain.repository.AccountRepository
import dagger.Binds
import dagger.Module

/**
 * Модуль Hilt для предоставления репозиториев модуля расходов.
 * Связывает интерфейс репозитория расходов с его реализацией.
 */
@Module
interface AccountRepositoryModule {

    @Binds
    @HistoryScope
    fun bindAccountRepository(impl: AccountRepositoryImpl): AccountRepository
}
