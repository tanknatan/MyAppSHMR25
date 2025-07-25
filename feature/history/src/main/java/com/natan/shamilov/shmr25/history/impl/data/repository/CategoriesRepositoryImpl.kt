package com.natan.shamilov.shmr25.history.impl.data.repository

import com.natan.shamilov.shmr25.common.api.CategoriesProvider
import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.common.impl.domain.entity.Category
import com.natan.shamilov.shmr25.history.impl.domain.repository.CategoriesRepository
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
    private val categoriesProvider: CategoriesProvider,
) : CategoriesRepository {

    override suspend fun getCategoriesList(isIncome: Boolean): List<Category> {
        return categoriesProvider.getCategoriesList()
            .filter { it.isIncome == isIncome }
    }
    override suspend fun getCategoryById(id: Int, isIncome: Boolean): Category? {
        return categoriesProvider.getCategoriesList()
            .firstOrNull { it.id.toInt() == id && it.isIncome == isIncome }
    }
}