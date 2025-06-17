package com.natan.shamilov.shmr25.domain.usecase

import com.natan.shamilov.shmr25.domain.FinanceRepository
import com.natan.shamilov.shmr25.domain.entity.Category
import javax.inject.Inject

class GetCategoriesListUseCase @Inject constructor(
    private val repository: FinanceRepository
) {

    operator fun invoke(): List<Category> {
        return repository.getCategoriesList()
    }
}