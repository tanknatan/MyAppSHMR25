package com.natan.shamilov.shmr25.common.impl.data.repository

import com.natan.shamilov.shmr25.common.api.TransactionsProvider
import com.natan.shamilov.shmr25.common.impl.data.api.TransactionsApi
import com.natan.shamilov.shmr25.common.impl.data.model.CreateTransactionRequestBody
import com.natan.shamilov.shmr25.common.impl.domain.repository.BaseTransactionsRepository
import com.natan.shamilov.shmr25.common.impl.data.model.Result
import javax.inject.Inject

class BaseTransacrionsRepositoryImpl @Inject constructor(
    private val api: TransactionsApi,
) : BaseTransactionsRepository, TransactionsProvider {

    override suspend fun createTransaction(
        accountId: Int,
        categoryId: Int,
        amount: String,
        transactionDate: String,
        comment: String,
    ): Result<Unit> = Result.execute {
        api.createTransaction(
            CreateTransactionRequestBody(
                accountId = accountId,
                categoryId = categoryId,
                amount = amount,
                transactionDate = transactionDate,
                comment = comment
            )
        )
    }

    override suspend fun editTransaction(
        transactionId: Int,
        accountId: Int,
        categoryId: Int,
        amount: String,
        transactionDate: String,
        comment: String,
    ): Result<Unit> = Result.execute {
        api.editTransaction(
            transactionId = transactionId,
            CreateTransactionRequestBody(
                accountId = accountId,
                categoryId = categoryId,
                amount = amount,
                transactionDate = transactionDate,
                comment = comment
            )
        )
    }
}