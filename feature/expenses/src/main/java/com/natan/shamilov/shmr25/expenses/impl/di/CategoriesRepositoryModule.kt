package com.natan.shamilov.shmr25.expenses.impl.di

import com.natan.shamilov.shmr25.expenses.impl.data.repository.CategoriesRepositoryImpl
import com.natan.shamilov.shmr25.expenses.impl.domain.repository.CategoriesRepository
import dagger.Binds
import dagger.Module

/**
 * Модуль Hilt для предоставления репозиториев модуля расходов.
 * Связывает интерфейс репозитория расходов с его реализацией.
 */
@Module
interface CategoriesRepositoryModule {

    @Binds
    @ExpensesScope
    fun bindCategoriesRepository(impl: CategoriesRepositoryImpl): CategoriesRepository
}
