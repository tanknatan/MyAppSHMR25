package com.natan.shamilov.shmr25.expenses.impl.domain.usecase

import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.expenses.impl.domain.entity.Expense
import com.natan.shamilov.shmr25.expenses.impl.domain.repository.ExpensesRepository
import javax.inject.Inject

class LoadExpensesByPeriodUseCase @Inject constructor(
    private val repository: ExpensesRepository,
) {
    suspend operator fun invoke(
        startDate: String,
        endDate: String,
    ): Result<List<Expense>> = repository.loadExpensesByPeriod(startDate, endDate)
}
