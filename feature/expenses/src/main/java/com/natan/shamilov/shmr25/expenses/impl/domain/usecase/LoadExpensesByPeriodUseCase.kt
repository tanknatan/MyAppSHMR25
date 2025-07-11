package com.natan.shamilov.shmr25.feature.expenses.domain.usecase

import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.feature.expenses.domain.entity.Expense
import com.natan.shamilov.shmr25.feature.expenses.domain.repository.ExpensesRepository
import javax.inject.Inject

class LoadExpensesByPeriodUseCase @Inject constructor(
    private val repository: ExpensesRepository
) {
    suspend operator fun invoke(
        startDate: String,
        endDate: String
    ): com.natan.shamilov.shmr25.common.impl.data.model.Result<List<Expense>> = repository.loadExpensesByPeriod(startDate, endDate)
}
