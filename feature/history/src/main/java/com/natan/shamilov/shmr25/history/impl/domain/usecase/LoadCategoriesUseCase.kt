package com.natan.shamilov.shmr25.history.impl.domain.usecase

import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.history.impl.domain.repository.CategoriesRepository
import javax.inject.Inject

class LoadCategoriesUseCase @Inject constructor(
    private val repository: CategoriesRepository,
) {
    suspend operator fun invoke(): Result<Unit> = repository.loadCategoriesList()
}
