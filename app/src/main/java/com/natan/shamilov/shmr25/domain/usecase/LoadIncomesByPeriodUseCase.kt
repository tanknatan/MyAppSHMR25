package com.natan.shamilov.shmr25.domain.usecase

import com.natan.shamilov.shmr25.data.api.Result
import com.natan.shamilov.shmr25.domain.FinanceRepository
import com.natan.shamilov.shmr25.domain.entity.Income
import javax.inject.Inject

class LoadIncomesByPeriodUseCase @Inject constructor(
    private val repository: FinanceRepository
) {

    suspend operator fun invoke(
        startDate: String,
        endDate: String
    ): Result<List<Income>> {
        return repository.loadIncomesByPeriod(
            startDate = startDate,
            endDate = endDate
        )
    }
}