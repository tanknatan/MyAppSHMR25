package com.natan.shamilov.shmr25.presentation.feature.categories.domain.repository

import com.natan.shamilov.shmr25.data.api.Result
import com.natan.shamilov.shmr25.domain.entity.Category

interface CategoriesRepository {
    suspend fun getCategoriesList(): Result<List<Category>>
} 