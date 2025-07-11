package com.natan.shamilov.shmr25.feature.categories.domain.usecase

import com.natan.shamilov.shmr25.common.impl.domain.entity.Category
import com.natan.shamilov.shmr25.feature.categories.domain.repository.CategoriesRepository
import javax.inject.Inject

class GetCategoriesListUseCase @Inject constructor(
    private val repository: CategoriesRepository
) {
    suspend operator fun invoke(): List<Category> = repository.getCategoriesList()
}
