package com.natan.shamilov.shmr25.categories.impl.domain.usecase

import com.natan.shamilov.shmr25.categories.impl.domain.repository.CategoriesRepository
import com.natan.shamilov.shmr25.common.impl.domain.entity.Category
import javax.inject.Inject

class GetCategoriesListUseCase @Inject constructor(
    private val categoriesRepository: CategoriesRepository,
) {
    suspend operator fun invoke(): List<Category> = categoriesRepository.getCategoriesList()
}
