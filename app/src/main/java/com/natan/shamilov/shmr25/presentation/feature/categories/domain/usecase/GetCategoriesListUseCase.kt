package com.natan.shamilov.shmr25.presentation.feature.categories.domain.usecase

import com.natan.shamilov.shmr25.data.api.Result
import com.natan.shamilov.shmr25.domain.entity.Category
import com.natan.shamilov.shmr25.presentation.feature.categories.domain.repository.CategoriesRepository
import javax.inject.Inject

class GetCategoriesListUseCase @Inject constructor(
    private val repository: CategoriesRepository
) {
    suspend operator fun invoke(): Result<List<Category>> = repository.getCategoriesList()
} 