package com.natan.shamilov.shmr25.common.api

import com.natan.shamilov.shmr25.common.impl.data.model.CreateTransactionResponse
import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.common.impl.data.model.TransactionDto
import com.natan.shamilov.shmr25.common.impl.domain.entity.Account
import com.natan.shamilov.shmr25.common.impl.domain.entity.Transaction

interface TransactionsProvider {

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

    suspend fun deleteTransaction(transactionId: Int): Result<Unit>

    suspend fun getTransactionById(
        transactionId: Int
    ): Result<Transaction>

     suspend fun getHistoryByPeriod(
        accounts: List<Account>,
        startDate: String,
        endDate: String,
        isIncome: Boolean
    ): Result<List<Transaction>>
}