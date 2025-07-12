package com.natan.shamilov.shmr25.expenses.impl.domain.usecase

import com.natan.shamilov.shmr25.expenses.impl.domain.entity.Expense
import com.natan.shamilov.shmr25.expenses.impl.domain.repository.ExpensesRepository
import javax.inject.Inject

class GetExpensesListUseCase @Inject constructor(
    private val repository: ExpensesRepository
) {
    suspend operator fun invoke(): List<Expense> = repository.getExpensesList()
}
