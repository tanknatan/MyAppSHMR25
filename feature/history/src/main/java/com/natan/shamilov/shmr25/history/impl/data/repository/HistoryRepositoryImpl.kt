package com.natan.shamilov.shmr25.history.impl.data.repository

import com.natan.shamilov.shmr25.common.api.AccountProvider
import com.natan.shamilov.shmr25.common.api.TransactionsProvider
import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.common.impl.domain.entity.Transaction
import com.natan.shamilov.shmr25.history.impl.domain.repository.HistoryRepository
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val accountProvider: AccountProvider,
    private val transactionsProvider: TransactionsProvider,
) : HistoryRepository {

    override suspend fun getTransactionList(
        startDate: String,
        endDate: String,
        isIncome: Boolean,
    ): Result<List<Transaction>> =
        transactionsProvider.getHistoryByPeriod(accountProvider.getAccountsList(), startDate, endDate, isIncome)

    override suspend fun getTransactionById(id: Int): Result<Transaction> = transactionsProvider.getTransactionById(id)

    override suspend fun deleteTransaction(transactionId: Int): Result<Unit> {
        TODO("Not yet implemented")
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
