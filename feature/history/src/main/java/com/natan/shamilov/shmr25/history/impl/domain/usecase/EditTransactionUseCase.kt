package com.natan.shamilov.shmr25.history.impl.domain.usecase

import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.history.impl.domain.repository.HistoryRepository
import javax.inject.Inject

class EditTransactionUseCase @Inject constructor(
    private val repository: HistoryRepository,
) {
    suspend operator fun invoke(
        transactionId: Int,
        accountId: Int,
        categoryId: Int,
        amount: String,
        transactionDate: String,
        comment: String,
    ): Result<Unit> = repository.editTransaction(
        transactionId = transactionId,
        accountId = accountId,
        categoryId = categoryId,
        amount = amount,
        transactionDate = transactionDate,
        comment = comment,
    )
}
