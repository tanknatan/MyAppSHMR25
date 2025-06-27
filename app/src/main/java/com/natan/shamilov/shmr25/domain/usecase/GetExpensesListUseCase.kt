package com.natan.shamilov.shmr25.domain.usecase

import com.natan.shamilov.shmr25.domain.FinanceRepository
import com.natan.shamilov.shmr25.domain.entity.Expense
import javax.inject.Inject

class GetExpensesListUseCase @Inject constructor(
    private val repository: FinanceRepository
) {
    suspend operator fun invoke(): List<Expense> = repository.getExpensesList()
}
