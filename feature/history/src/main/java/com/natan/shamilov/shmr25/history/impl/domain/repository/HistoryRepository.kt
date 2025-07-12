package com.natan.shamilov.shmr25.history.impl.domain.repository

import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.history.impl.domain.model.HistoryItem

interface HistoryRepository {
    suspend fun getHistoryByPeriod(
        startDate: String,
        endDate: String,
        isIncome: Boolean,
    ): com.natan.shamilov.shmr25.common.impl.data.model.Result<List<HistoryItem>>

    suspend fun getHistoryItemById(id: Int): HistoryItem?

    suspend fun deleteTransaction(transactionId: Int): Result<Unit>

    suspend fun createTransaction(
        accountId: Int,
        categoryId: Int,
        amount: String,
        transactionDate: String,
        comment: String,
    ): Result<Unit>

    suspend fun editTransaction(
        transactionId: Int,
        accountId: Int,
        categoryId: Int,
        amount: String,
        transactionDate: String,
        comment: String,
    ): Result<Unit>
}
