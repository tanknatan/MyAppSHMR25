package com.natan.shamilov.shmr25.feature.categories.domain.repository

import com.natan.shamilov.shmr25.app.data.api.Result
import com.natan.shamilov.shmr25.common.Category

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
    suspend fun getCategoriesList(): Result<List<Category>>
}
