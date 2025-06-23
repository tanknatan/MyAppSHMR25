package com.natan.shamilov.shmr25.domain.usecase

import com.natan.shamilov.shmr25.data.api.Result
import com.natan.shamilov.shmr25.domain.FinanceRepository
import com.natan.shamilov.shmr25.domain.entity.Expense
import javax.inject.Inject

class LoadExpensesByPeriodUseCase @Inject constructor(
    private val repository: FinanceRepository
) {

    suspend operator fun invoke(
        startDate: String,
        endDate: String
    ): Result<List<Expense>> {
        return repository.loadExpensesByPeriod(
            startDate = startDate,
            endDate = endDate
        )
    }
}