package com.natan.shamilov.shmr25.domain.usecase

import com.natan.shamilov.shmr25.domain.FinanceRepository
import com.natan.shamilov.shmr25.domain.entity.Income
import javax.inject.Inject

class GetIncomesListUseCase @Inject constructor(
    private val repository: FinanceRepository
) {
    operator fun invoke(): List<Income> {
        return repository.getIncomesList()
    }
}