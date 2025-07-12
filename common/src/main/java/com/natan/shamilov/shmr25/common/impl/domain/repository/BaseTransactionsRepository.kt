package com.natan.shamilov.shmr25.common.impl.domain.repository

import com.natan.shamilov.shmr25.common.impl.data.model.Result

interface BaseTransactionsRepository {
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