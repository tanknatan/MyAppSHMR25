package com.natan.shamilov.shmr25.expenses.impl.domain.usecase

import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.common.impl.domain.entity.Transaction
import com.natan.shamilov.shmr25.expenses.impl.domain.repository.ExpensesRepository
import javax.inject.Inject

class GetExpenseByIdUseCase @Inject constructor(
    private val repository: ExpensesRepository,
) {
    suspend operator fun invoke(id: Int): Result<Transaction> = repository.getTransactionById(id)
}