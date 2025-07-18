package com.natan.shamilov.shmr25.history.impl.domain.repository

import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.common.impl.domain.entity.Transaction

interface HistoryRepository {
    suspend fun getTransactionList(startDate: String, endDate: String, isIncome: Boolean): Result<List<Transaction>>

    suspend fun getTransactionById(id: Int): Result<Transaction>

    suspend fun deleteTransaction(transactionId: Int): Result<Unit>

    suspend fun editTransaction(
        transactionId: Int,
        accountId: Int,
        categoryId: Int,
        amount: String,
        transactionDate: String,
        comment: String?,
    ): Result<Unit>
}
