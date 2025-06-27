package com.natan.shamilov.shmr25.presentation.feature.categories.data.api

import com.natan.shamilov.shmr25.data.api.model.CategoryDto
import retrofit2.http.GET

interface CategoriesApi {
    @GET("categories")
    suspend fun getCategories(): List<CategoryDto>

    companion object {
        const val BASE_URL = "https://shmr-finance.ru/api/v1/"
    }
} 