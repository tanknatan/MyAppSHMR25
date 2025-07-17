package com.natan.shamilov.shmr25.incomes.impl.data.repository

import com.natan.shamilov.shmr25.common.api.CategoriesProvider
import com.natan.shamilov.shmr25.common.impl.domain.entity.Category
import com.natan.shamilov.shmr25.incomes.impl.domain.repository.CategoriesRepository
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
    private val categoriesProvider: CategoriesProvider,
) : CategoriesRepository {
    override suspend fun getCategoriesList(): List<Category> {
        return categoriesProvider.getCategoriesList()
            .filter { it.isIncome == true }
    }

    override suspend fun getCategoryById(id: Int): Category? {
        return categoriesProvider.getCategoriesList()
            .firstOrNull { it.id.toInt() == id && it.isIncome == true }
    }
}