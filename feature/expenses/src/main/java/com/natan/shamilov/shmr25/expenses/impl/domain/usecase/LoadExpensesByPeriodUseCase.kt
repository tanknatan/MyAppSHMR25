package com.natan.shamilov.shmr25.expenses.impl.domain.usecase

import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.common.impl.domain.entity.Transaction
import com.natan.shamilov.shmr25.expenses.impl.domain.repository.ExpensesRepository
import javax.inject.Inject

class LoadExpensesByPeriodUseCase @Inject constructor(
    private val repository: ExpensesRepository,
) {
    suspend operator fun invoke(): Result<List<Transaction>> = repository.getTransactionList()
}
