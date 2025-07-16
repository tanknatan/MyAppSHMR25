package com.natan.shamilov.shmr25.common.api

import com.natan.shamilov.shmr25.common.impl.data.model.Result

interface TransactionsProvider {
    suspend fun createTransaction(
        accountId: Int,
        categoryId: Int,
        amount: String,
        transactionDate: String,
        comment: String,
    ): Result<Unit>

    suspend fun editTransaction(
        transactionId: Int,
        categoryId: Int,
        accountId: Int,
        amount: String,
        transactionDate: String,
        comment: String,
    ): Result<Unit>
}