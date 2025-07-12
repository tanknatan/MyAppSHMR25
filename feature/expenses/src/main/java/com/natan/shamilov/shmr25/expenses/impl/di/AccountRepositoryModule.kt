package com.natan.shamilov.shmr25.expenses.impl.di

import com.natan.shamilov.shmr25.expenses.impl.data.repository.AccountRepositoryImpl
import com.natan.shamilov.shmr25.expenses.impl.domain.repository.AccountRepository
import dagger.Binds
import dagger.Module

/**
 * Модуль Hilt для предоставления репозиториев модуля расходов.
 * Связывает интерфейс репозитория расходов с его реализацией.
 */
@Module
interface AccountRepositoryModule {

    @Binds
    @ExpensesScope
    fun bindAccountRepository(impl: AccountRepositoryImpl): AccountRepository
}
