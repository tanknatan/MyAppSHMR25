package com.natan.shamilov.shmr25.common.impl.data.api

import com.natan.shamilov.shmr25.common.impl.data.model.CategoryDto
import retrofit2.http.GET

/**
 * Определяет контракт для взаимодействия с API категорий.
 * Ответственность: Описание доступных HTTP-эндпоинтов для операций с категориями,
 * включая получение списка всех категорий.
 */
interface CategoriesApi {
    /**
     * Получает список всех категорий
     * @return список категорий в формате
     */
    @GET("categories")
    suspend fun getCategories(): List<CategoryDto>
}
