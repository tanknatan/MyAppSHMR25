package com.natan.shamilov.shmr25.domain.usecase

import com.natan.shamilov.shmr25.data.api.Result
import com.natan.shamilov.shmr25.domain.FinanceRepository
import com.natan.shamilov.shmr25.domain.entity.Category
import javax.inject.Inject

class GetCategoriesListUseCase @Inject constructor(
    private val repository: FinanceRepository
) {
    suspend operator fun invoke(): Result<List<Category>> = repository.getCategoriesList()
}
