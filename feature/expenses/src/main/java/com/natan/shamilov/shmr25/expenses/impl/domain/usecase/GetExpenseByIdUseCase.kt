package com.natan.shamilov.shmr25.expenses.impl.domain.usecase

import com.natan.shamilov.shmr25.expenses.impl.domain.repository.ExpensesRepository
import com.natan.shamilov.shmr25.expenses.impl.domain.entity.Expense
import javax.inject.Inject

class GetExpenseByIdUseCase @Inject constructor(
    private val repository: ExpensesRepository,
) {
    suspend operator fun invoke(id: Int): Expense? = repository.getExpenseById(id)
}