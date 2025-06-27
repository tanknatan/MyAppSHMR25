package com.natan.shamilov.shmr25.presentation.feature.expenses.domain.usecase

import com.natan.shamilov.shmr25.data.api.Result
import com.natan.shamilov.shmr25.domain.entity.Expense
import com.natan.shamilov.shmr25.presentation.feature.expenses.domain.repository.ExpensesRepository
import javax.inject.Inject

class LoadExpensesByPeriodUseCase @Inject constructor(
    private val repository: ExpensesRepository
) {
    suspend operator fun invoke(
        startDate: String,
        endDate: String
    ): Result<List<Expense>> = repository.loadExpensesByPeriod(startDate, endDate)
} 