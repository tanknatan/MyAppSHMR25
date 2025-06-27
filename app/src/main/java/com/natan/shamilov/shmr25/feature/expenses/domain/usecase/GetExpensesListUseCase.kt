package com.natan.shamilov.shmr25.feature.expenses.domain.usecase

import com.natan.shamilov.shmr25.feature.expenses.domain.entity.Expense
import com.natan.shamilov.shmr25.feature.expenses.domain.repository.ExpensesRepository
import javax.inject.Inject

class GetExpensesListUseCase @Inject constructor(
    private val repository: ExpensesRepository
) {
    suspend operator fun invoke(): List<Expense> = repository.getExpensesList()
}
