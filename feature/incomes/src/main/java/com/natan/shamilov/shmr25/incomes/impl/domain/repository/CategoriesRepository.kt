package com.natan.shamilov.shmr25.incomes.impl.domain.repository

import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.common.impl.domain.entity.Category

interface CategoriesRepository {
    suspend fun loadCategoriesList(): Result<Unit>

    suspend fun getCategoriesList(): List<Category>

    suspend fun getCategoryById(id: Int): Category?
}