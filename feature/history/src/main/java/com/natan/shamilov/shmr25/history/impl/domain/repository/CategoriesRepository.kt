package com.natan.shamilov.shmr25.history.impl.domain.repository

import com.natan.shamilov.shmr25.common.impl.domain.entity.Category

interface CategoriesRepository {
    suspend fun getCategoriesList(isIncome: Boolean): List<Category>

    suspend fun getCategoryById(id: Int, isIncome: Boolean): Category?
}