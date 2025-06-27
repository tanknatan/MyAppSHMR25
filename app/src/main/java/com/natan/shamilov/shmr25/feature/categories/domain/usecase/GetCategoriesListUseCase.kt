package com.natan.shamilov.shmr25.feature.categories.domain.usecase

import com.natan.shamilov.shmr25.common.Category
import com.natan.shamilov.shmr25.app.data.api.Result
import com.natan.shamilov.shmr25.feature.categories.domain.repository.CategoriesRepository
import javax.inject.Inject

class GetCategoriesListUseCase @Inject constructor(
    private val repository: CategoriesRepository
) {
    suspend operator fun invoke(): Result<List<Category>> = repository.getCategoriesList()
}
