package com.natan.shamilov.shmr25.incomes.impl.data.repository

import com.natan.shamilov.shmr25.common.api.AccountProvider
import com.natan.shamilov.shmr25.common.api.TransactionsProvider
import com.natan.shamilov.shmr25.common.impl.data.model.CreateTransactionResponse
import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.common.impl.domain.entity.Transaction
import com.natan.shamilov.shmr25.common.impl.presentation.utils.getUtcDayBounds
import com.natan.shamilov.shmr25.incomes.impl.domain.repository.IncomesRepository
import javax.inject.Inject

class IncomesRepositoryImpl @Inject constructor(
    private val accountProvider: AccountProvider,
    private val transactionsProvider: TransactionsProvider,
) : IncomesRepository {

    override suspend fun getTransactionList(): Result<List<Transaction>> {
        val (start, end) = getUtcDayBounds(java.time.LocalDate.now())
        return transactionsProvider.getHistoryByPeriod(accountProvider.getAccountsList(), start, end, true)
    }

    override suspend fun getTransactionById(id: Int): Result<Transaction> = transactionsProvider.getTransactionById(id)

    override suspend fun deleteTransaction(transactionId: Int): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun createTransaction(
        accountId: Int,
        categoryId: Int,
        amount: String,
        transactionDate: String,
        comment: String?,
    ): Result<CreateTransactionResponse> {
        return transactionsProvider.createTransaction(
            accountId = accountId,
            categoryId = categoryId,
            amount = amount,
            transactionDate = transactionDate,
            comment = comment
        )
    }

    override suspend fun editTransaction(
        transactionId: Int,
        accountId: Int,
        categoryId: Int,
        amount: String,
        transactionDate: String,
        comment: String?,
    ): Result<Unit> {
        return transactionsProvider.editTransaction(
            transactionId = transactionId,
            accountId = accountId,
            categoryId = categoryId,
            amount = amount,
            transactionDate = transactionDate,
            comment = comment
        )
    }
}
