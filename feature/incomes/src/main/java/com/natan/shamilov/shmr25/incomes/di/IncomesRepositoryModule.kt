package com.natan.shamilov.shmr25.incomes.di

import com.natan.shamilov.shmr25.feature.incomes.data.repository.IncomesRepositoryImpl
import com.natan.shamilov.shmr25.feature.incomes.domain.repository.IncomesRepository
import dagger.Binds
import dagger.Module

/**
 * Dagger Hilt модуль для внедрения зависимостей репозитория доходов.
 * Обеспечивает привязку реализации репозитория к его интерфейсу
 * в рамках жизненного цикла синглтона.
 */
@Module
interface IncomesRepositoryModule {

    @Binds
    @IncomesScope
    fun bindIncomesRepository(impl: IncomesRepositoryImpl): IncomesRepository
}