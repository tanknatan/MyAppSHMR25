package com.natan.shamilov.shmr25.feature.categories.data.api

import com.natan.shamilov.shmr25.app.data.api.model.CategoryDto
import retrofit2.http.GET

/**
 * Определяет контракт для взаимодействия с API категорий.
 * Ответственность: Описание доступных HTTP-эндпоинтов для операций с категориями,
 * включая получение списка всех категорий.
 */
interface CategoriesApi {
    /**
     * Получает список всех категорий
     * @return список категорий в формате DTO
     */
    @GET("categories")
    suspend fun getCategories(): List<CategoryDto>
}
