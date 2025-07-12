package com.natan.shamilov.shmr25.common.api

import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.common.impl.domain.entity.Category

interface CategoriesProvider {
    suspend fun getCategoriesList(): List<Category>
    suspend fun loadCategoriesList(): Result<Unit>
}