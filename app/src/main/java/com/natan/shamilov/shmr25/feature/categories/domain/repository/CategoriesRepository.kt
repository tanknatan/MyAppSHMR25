package com.natan.shamilov.shmr25.feature.categories.domain.repository

import com.natan.shamilov.shmr25.common.data.model.Result
import com.natan.shamilov.shmr25.feature.categories.domain.entity.Category

/**
 * Интерфейс репозитория для работы с категориями.
 * Ответственность: Определение контракта для операций с категориями в доменном слое,
 * включая получение списка всех доступных категорий.
 */
interface CategoriesRepository {
    /**
     * Получает список всех категорий
     * @return результат операции со списком категорий
     */
    suspend fun loadCategoriesList(): Result<Unit>

    suspend fun getCategoriesList(): List<Category>
}
