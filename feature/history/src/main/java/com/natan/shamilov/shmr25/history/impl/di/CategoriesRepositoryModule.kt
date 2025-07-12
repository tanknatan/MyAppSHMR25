package com.natan.shamilov.shmr25.history.impl.di

import com.natan.shamilov.shmr25.history.impl.data.repository.CategoriesRepositoryImpl
import com.natan.shamilov.shmr25.history.impl.domain.repository.CategoriesRepository
import dagger.Binds
import dagger.Module

/**
 * Модуль Hilt для предоставления репозиториев модуля расходов.
 * Связывает интерфейс репозитория расходов с его реализацией.
 */
@Module
interface CategoriesRepositoryModule {

    @Binds
    @HistoryScope
    fun bindCategoriesRepository(impl: CategoriesRepositoryImpl): CategoriesRepository
}
