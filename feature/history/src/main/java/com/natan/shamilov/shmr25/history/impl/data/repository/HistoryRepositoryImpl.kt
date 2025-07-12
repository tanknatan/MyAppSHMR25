package com.natan.shamilov.shmr25.history.impl.data.repository

import com.natan.shamilov.shmr25.common.api.AccountProvider
import com.natan.shamilov.shmr25.common.api.TransactionsProvider
import com.natan.shamilov.shmr25.common.impl.data.api.TransactionsApi
import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.feature.history.data.mapper.HistoryMapper
import com.natan.shamilov.shmr25.history.impl.domain.repository.HistoryRepository
import com.natan.shamilov.shmr25.history.impl.domain.model.HistoryItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val api: TransactionsApi,
    private val historyMapper: HistoryMapper,
    private val accountProvider: AccountProvider,
    private val transactionsProvider: TransactionsProvider,
) : HistoryRepository {

    override suspend fun getHistoryByPeriod(
        startDate: String,
        endDate: String,
        isIncome: Boolean,
    ): Result<List<HistoryItem>> = Result.execute {
        val accounts = accountProvider.getAccountsList()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        coroutineScope {
            accounts.map { account ->
                async {
                    api.getTransactionsByAccountPeriod(
                        accountId = account.id,
                        startDate = startDate,
                        endDate = endDate
                    )
                }
            }
        }.awaitAll().flatten()
            .filter { it.category.isIncome == isIncome }
            .sortedByDescending { transaction ->
                LocalDate.parse(
                    transaction.createdAt.substring(START_OF_DATA_IN_RESPONSE, END_OF_DATA_IN_RESPONSE),
                    formatter
                )
            }
            .map { historyMapper.mapTransactionDtoToHistoryItem(it) }
    }

    override suspend fun getHistoryItemById(id: Int): HistoryItem? = withContext(Dispatchers.IO) {
        return@withContext try {
            val dto = api.getTransactionById(id)
            historyMapper.mapTransactionDtoToHistoryItem(dto)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun deleteTransaction(transactionId: Int): Result<Unit> = Result.execute {
        api.deleteTransaction(transactionId)
    }

    override suspend fun createTransaction(
        accountId: Int,
        categoryId: Int,
        amount: String,
        transactionDate: String,
        comment: String,
    ): Result<Unit> {
        return transactionsProvider.createTransaction(accountId, categoryId, amount, transactionDate, comment)
    }

    override suspend fun editTransaction(
        transactionId: Int,
        accountId: Int,
        categoryId: Int,
        amount: String,
        transactionDate: String,
        comment: String,
    ): Result<Unit> {
        return transactionsProvider.editTransaction(
            transactionId,
            accountId,
            categoryId,
            amount,
            transactionDate,
            comment
        )
    }

    companion object {
        private const val START_OF_DATA_IN_RESPONSE = 0
        private const val END_OF_DATA_IN_RESPONSE = 10
    }
}
