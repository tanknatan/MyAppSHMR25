package com.natan.shamilov.shmr25.common.impl.data.repository

import android.util.Log
import com.natan.shamilov.shmr25.common.api.NetworkChekerProvider
import com.natan.shamilov.shmr25.common.api.TransactionsProvider
import com.natan.shamilov.shmr25.common.impl.data.api.TransactionsApi
import com.natan.shamilov.shmr25.common.impl.data.mapper.TransactionMapper
import com.natan.shamilov.shmr25.common.impl.data.model.CreateTransactionRequestBody
import com.natan.shamilov.shmr25.common.impl.data.model.CreateTransactionResponse
import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.common.impl.data.model.TransactionDto
import com.natan.shamilov.shmr25.common.impl.domain.entity.Account
import com.natan.shamilov.shmr25.common.impl.domain.entity.Transaction
import com.natan.shamilov.shmr25.common.impl.domain.repository.BaseTransactionsRepository
import com.natan.shamilov.shmr25.common.impl.domain.storage.dao.TransactionsDao
import com.natan.shamilov.shmr25.common.impl.domain.storage.entity.TransactionDbModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class BaseTransacrionsRepositoryImpl @Inject constructor(
    private val api: TransactionsApi,
    private val networkRepository: NetworkChekerProvider,
    private val mapper: TransactionMapper,
    private val transactionsDao: TransactionsDao
) : BaseTransactionsRepository, TransactionsProvider {

    override suspend fun deleteTransaction(transactionId: Int): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getHistoryByPeriod(
        accounts: List<Account>,
        startDate: String,
        endDate: String,
        isIncome: Boolean
    ): Result<List<Transaction>> = Result.execute {
        if (networkRepository.getNetworkStatus().value) {
            loadHistoryFromNetwork(
                accounts = accounts,
                startDate = startDate,
                endDate = endDate,
                isIncome = isIncome
            )
        } else {
            loadHistoryFromDb(
                startDate = startDate,
                endDate = endDate,
                isIncome = isIncome
            )
        }
    }

    private suspend fun loadHistoryFromNetwork(
        accounts: List<Account>,
        startDate: String,
        endDate: String,
        isIncome: Boolean
    ): List<Transaction> {
        val historyDto = coroutineScope {
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
            .sortedByDescending { it.createdAt }

        val historyDomain = historyDto
            .map { dto ->
                mapper.mapTransactionDtoToTransaction(dto)
            }

        val historyDb = historyDto
            .map { dto ->
                mapper.mapTransactionDtoToDbModel(dto)
            }

        transactionsDao.insertTransactionsList(historyDb)

        return historyDomain
    }

    private suspend fun loadHistoryFromDb(
        startDate: String,
        endDate: String,
        isIncome: Boolean
    ): List<Transaction> {
        return transactionsDao.getTransactionsByPeriod(
            startDate = startDate,
            endDate = endDate
        ).filter { it.category.isIncome == isIncome }
            .map { db ->
                mapper.mapTransactionDbToTransaction(db)
            }
    }

    override suspend fun createTransaction(
        accountId: Int,
        categoryId: Int,
        amount: String,
        transactionDate: String,
        comment: String?
    ): Result<CreateTransactionResponse> = Result.execute {
        if (networkRepository.getNetworkStatus().value) {
            createNetworkTransaction(
                accountId = accountId,
                categoryId = categoryId,
                amount = amount,
                transactionDate = transactionDate,
                comment = comment
            )
        } else {
            createLocalTransaction(
                accountId = accountId,
                categoryId = categoryId,
                amount = amount,
                transactionDate = transactionDate,
                comment = comment
            )
        }
    }

    private suspend fun createLocalTransaction(
        accountId: Int,
        categoryId: Int,
        amount: String,
        transactionDate: String,
        comment: String?
    ): CreateTransactionResponse {
        val currentTime = -System.currentTimeMillis().toInt()
        val transactionDb = TransactionDbModel(
            id = currentTime,
            amount = amount.toDouble(),
            comment = comment,
            createdAt = transactionDate,
            accountId = accountId,
            categoryId = categoryId,
            localId = currentTime.toString(),
            syncStatus = "pending"
        )
        transactionsDao.insertTransaction(transactionDb)
        return mapper.mapTransactionDbModelToResponse(transactionDb)
    }

    private suspend fun createNetworkTransaction(
        accountId: Int,
        categoryId: Int,
        amount: String,
        transactionDate: String,
        comment: String?
    ): CreateTransactionResponse {
        val response = api.createTransaction(
            CreateTransactionRequestBody(
                accountId = accountId,
                categoryId = categoryId,
                amount = amount,
                transactionDate = transactionDate,
                comment = comment
            )
        )
        transactionsDao.insertTransaction(mapper.mapTransactionResponseToDbModel(response))
        return response
    }

    override suspend fun editTransaction(
        transactionId: Int,
        accountId: Int,
        categoryId: Int,
        amount: String,
        transactionDate: String,
        comment: String?
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

    override suspend fun getTransactionById(transactionId: Int): Result<Transaction> =
        Result.execute {
            if (networkRepository.getNetworkStatus().value) {
                getTransactionFromNetwork(transactionId)
            } else {
                getTransactionFromDb(transactionId)
            }
        }

    override suspend fun syncTransactions(): Result<Unit> = Result.execute {
        val pendingTransactionsDb = transactionsDao.getPendingTransactions()
        Log.d("SyncStatus", "Pending transactions: $pendingTransactionsDb")

        pendingTransactionsDb.forEach { transaction ->
            if (transaction.localId != null) {
                when (
                    val response = createTransaction(
                        accountId = transaction.accountId,
                        categoryId = transaction.categoryId,
                        amount = transaction.amount.toString(),
                        transactionDate = transaction.createdAt,
                        comment = transaction.comment
                    )
                ) {
                    is Result.Error -> {
                        Log.d("SyncStatus", response.exception.toString())
                    }
                    is Result.Loading -> {}
                    is Result.Success<CreateTransactionResponse> -> {
                        try {
                            Log.d("SyncStatus", "Pending Transaction id: ${transaction.id}")
                            transactionsDao.deleteTransactionById(transaction.id)
                            transactionsDao.insertTransaction(
                                mapper.mapTransactionResponseToDbModel(
                                    response.data
                                )
                            )
                        } catch (e: Exception) {
                            Log.d("SyncStatus", e.message.toString())
                        }
                    }
                }
            } else {
                when (
                    editTransaction(
                        transactionId = transaction.id,
                        accountId = transaction.accountId,
                        categoryId = transaction.categoryId,
                        amount = transaction.amount.toString(),
                        transactionDate = transaction.createdAt,
                        comment = transaction.comment
                    )
                ) {
                    is Result.Error -> {}
                    Result.Loading -> {}
                    is Result.Success<*> -> {
                        transactionsDao.changeTransactionSyncStatus(
                            transactionId = transaction.id
                        )
                    }
                }
            }
        }
    }

    private suspend fun getTransactionFromNetwork(transactionId: Int): Transaction {
        val transactionDto = api.getTransactionById(transactionId)
        val transaction = mapper.mapTransactionDtoToTransaction(transactionDto)
        return transaction
    }

    private suspend fun getTransactionFromDb(transactionId: Int): Transaction {
        val transactionDb = transactionsDao.getTransactionById(transactionId)

        return mapper.mapTransactionDbToTransaction(transactionDb)
    }
}
