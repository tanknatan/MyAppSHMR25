package com.natan.shamilov.shmr25.expenses.impl.di

import com.natan.shamilov.shmr25.expenses.impl.data.repository.ExpensesRepositoryImpl
import com.natan.shamilov.shmr25.expenses.impl.domain.repository.ExpensesRepository
import dagger.Binds
import dagger.Module

/**
 * Модуль Hilt для предоставления репозиториев модуля расходов.
 * Связывает интерфейс репозитория расходов с его реализацией.
 */
@Module
interface ExpensesRepositoryModule {

    @Binds
    @ExpensesScope
    fun bindExpensesRepository(impl: ExpensesRepositoryImpl): ExpensesRepository
}
