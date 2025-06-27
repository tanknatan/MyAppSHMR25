package com.natan.shamilov.shmr25.presentation.feature.categories.data.repository

import com.natan.shamilov.shmr25.data.api.Result
import com.natan.shamilov.shmr25.data.api.mapper.FinanceMapper
import com.natan.shamilov.shmr25.domain.entity.Category
import com.natan.shamilov.shmr25.presentation.feature.categories.data.api.CategoriesApi
import com.natan.shamilov.shmr25.presentation.feature.categories.domain.repository.CategoriesRepository
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
    private val api: CategoriesApi,
    private val mapper: FinanceMapper
) : CategoriesRepository {

    override suspend fun getCategoriesList(): Result<List<Category>> = Result.execute {
        api.getCategories().map { dto ->
            mapper.mapCategoryDtoToDomain(dto)
        }
    }
} 