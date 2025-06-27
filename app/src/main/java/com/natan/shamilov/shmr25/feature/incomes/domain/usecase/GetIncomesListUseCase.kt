package com.natan.shamilov.shmr25.feature.incomes.domain.usecase

import com.natan.shamilov.shmr25.feature.incomes.domain.entity.Income
import com.natan.shamilov.shmr25.feature.incomes.domain.repository.IncomesRepository
import javax.inject.Inject

class GetIncomesListUseCase @Inject constructor(
    private val repository: IncomesRepository
) {
    suspend operator fun invoke(): List<Income> = repository.getIncomesList()
} 