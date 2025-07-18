package com.natan.shamilov.shmr25.incomes.impl.domain.usecase

import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.incomes.impl.domain.repository.IncomesRepository
import javax.inject.Inject

class EditIncomeUseCase @Inject constructor(
    private val repository: IncomesRepository,
) {
    suspend operator fun invoke(
        transactionId: Int,
        accountId: Int,
        categoryId: Int,
        amount: String,
        transactionDate: String,
        comment: String?,
    ): Result<Unit> = repository.editTransaction(
        transactionId = transactionId,
        accountId = accountId,
        categoryId = categoryId,
        amount = amount,
        transactionDate = transactionDate,
        comment = comment,
    )
}
