package com.natan.shamilov.shmr25.incomes.impl.domain.repository

import com.natan.shamilov.shmr25.common.impl.domain.entity.Category

interface CategoriesRepository {
    suspend fun getCategoriesList(): List<Category>

    suspend fun getCategoryById(id: Int): Category?
}