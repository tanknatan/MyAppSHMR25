package com.natan.shamilov.shmr25.incomes.impl.domain.usecase

import com.natan.shamilov.shmr25.incomes.impl.domain.entity.Income
import com.natan.shamilov.shmr25.incomes.impl.domain.repository.IncomesRepository
import javax.inject.Inject

class GetIncomeByIdUseCase @Inject constructor(
    private val repository: IncomesRepository,
) {
    suspend operator fun invoke(id: Int): Income? = repository.getIncomeById(id)
}