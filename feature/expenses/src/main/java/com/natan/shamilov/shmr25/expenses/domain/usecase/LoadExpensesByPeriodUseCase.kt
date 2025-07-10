package com.natan.shamilov.shmr25.feature.expenses.domain.usecase

import com.natan.shamilov.shmr25.common.data.model.Result
import com.natan.shamilov.shmr25.feature.expenses.domain.entity.Expense
import com.natan.shamilov.shmr25.feature.expenses.domain.repository.ExpensesRepository
import javax.inject.Inject

class LoadExpensesByPeriodUseCase @Inject constructor(
    private val repository: ExpensesRepository
) {
    suspend operator fun invoke(
        startDate: String,
        endDate: String
    ): Result<List<Expense>> = repository.loadExpensesByPeriod(startDate, endDate)
}
