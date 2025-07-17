package com.natan.shamilov.shmr25.categories.impl.data.repository

import com.natan.shamilov.shmr25.categories.impl.domain.repository.CategoriesRepository
import com.natan.shamilov.shmr25.common.api.CategoriesProvider
import com.natan.shamilov.shmr25.common.impl.domain.entity.Category
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
    private val categoriesProvider: CategoriesProvider,
) : CategoriesRepository {
    override suspend fun getCategoriesList(): List<Category> {
        return categoriesProvider.getCategoriesList()
    }
}