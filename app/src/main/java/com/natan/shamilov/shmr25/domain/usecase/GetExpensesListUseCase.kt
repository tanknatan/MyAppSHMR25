package com.natan.shamilov.shmr25.domain.usecase

import com.natan.shamilov.shmr25.domain.FinanceRepository
import com.natan.shamilov.shmr25.domain.entity.Expense
import javax.inject.Inject

class GetExpensesListUseCase @Inject constructor(
    private val repository: FinanceRepository
) {

    operator fun invoke(): List<Expense> {
        return repository.getExpensesList()
    }
}