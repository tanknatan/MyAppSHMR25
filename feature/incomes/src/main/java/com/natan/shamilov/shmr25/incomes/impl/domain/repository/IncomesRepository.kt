package com.natan.shamilov.shmr25.incomes.impl.domain.repository

import com.natan.shamilov.shmr25.common.impl.data.model.CreateTransactionResponse
import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.common.impl.domain.entity.Transaction

interface IncomesRepository {

    suspend fun getTransactionList(): Result<List<Transaction>>

    suspend fun getTransactionById(id: Int): Result<Transaction>

    suspend fun deleteTransaction(transactionId: Int): Result<Unit>

    suspend fun createTransaction(
        accountId: Int,
        categoryId: Int,
        amount: String,
        transactionDate: String,
        comment: String?,
    ): Result<CreateTransactionResponse>

    suspend fun editTransaction(
        transactionId: Int,
        accountId: Int,
        categoryId: Int,
        amount: String,
        transactionDate: String,
        comment: String?,
    ): Result<Unit>

}
