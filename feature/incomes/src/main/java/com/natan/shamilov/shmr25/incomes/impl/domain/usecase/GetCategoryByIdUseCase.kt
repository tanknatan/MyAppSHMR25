package com.natan.shamilov.shmr25.incomes.impl.domain.usecase

import com.natan.shamilov.shmr25.common.impl.domain.entity.Category
import com.natan.shamilov.shmr25.incomes.impl.domain.repository.CategoriesRepository
import javax.inject.Inject

class GetCategoryByIdUseCase @Inject constructor(
    private val repository: CategoriesRepository,
) {
    suspend operator fun invoke(id: Int): Category? = repository.getCategoryById(id)
}