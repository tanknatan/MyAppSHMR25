package com.natan.shamilov.shmr25.incomes.impl.di

import com.natan.shamilov.shmr25.incomes.impl.data.repository.AccountRepositoryImpl
import com.natan.shamilov.shmr25.incomes.impl.domain.repository.AccountRepository
import dagger.Binds
import dagger.Module

/**
 * Модуль Hilt для предоставления репозиториев модуля расходов.
 * Связывает интерфейс репозитория расходов с его реализацией.
 */
@Module
interface AccountRepositoryModule {

    @Binds
    @IncomesScope
    fun bindAccountRepository(impl: AccountRepositoryImpl): AccountRepository
}
