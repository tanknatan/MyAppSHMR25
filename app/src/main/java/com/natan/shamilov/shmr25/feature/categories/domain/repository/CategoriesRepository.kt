package com.natan.shamilov.shmr25.feature.categories.domain.repository

import com.natan.shamilov.shmr25.app.data.api.Result
import com.natan.shamilov.shmr25.common.Category

interface CategoriesRepository {
    suspend fun getCategoriesList(): Result<List<Category>>
} 
