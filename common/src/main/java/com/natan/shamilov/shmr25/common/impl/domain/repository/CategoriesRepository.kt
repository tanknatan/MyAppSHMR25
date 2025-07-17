package com.natan.shamilov.shmr25.common.impl.domain.repository

import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.common.impl.domain.entity.Category

/**
 * Интерфейс репозитория для работы с категориями.
 * Ответственность: Определение контракта для операций с категориями в доменном слое,
 * включая получение списка всех доступных категорий.
 */
internal interface CategoriesRepository {
    /**
     * Получает список всех категорий
     * @return результат операции со списком категорий
     */
    suspend fun getCategoriesList(): List<Category>
}
