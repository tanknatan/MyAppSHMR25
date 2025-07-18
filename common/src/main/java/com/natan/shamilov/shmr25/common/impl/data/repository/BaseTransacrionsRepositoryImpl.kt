package com.natan.shamilov.shmr25.common.impl.data.repository

import com.natan.shamilov.shmr25.common.api.NetworkChekerProvider
import com.natan.shamilov.shmr25.common.api.TransactionsProvider
import com.natan.shamilov.shmr25.common.impl.data.api.TransactionsApi
import com.natan.shamilov.shmr25.common.impl.data.mapper.TransactionMapper
import com.natan.shamilov.shmr25.common.impl.data.model.CreateTransactionRequestBody
import com.natan.shamilov.shmr25.common.impl.data.model.CreateTransactionResponse
import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.common.impl.domain.entity.Account
import com.natan.shamilov.shmr25.common.impl.domain.entity.Transaction
import com.natan.shamilov.shmr25.common.impl.domain.repository.BaseTransactionsRepository
import com.natan.shamilov.shmr25.common.impl.domain.storage.dao.TransactionsDao
import com.natan.shamilov.shmr25.common.impl.domain.storage.entity.TransactionDbModel
import com.natan.shamilov.shmr25.common.impl.presentation.utils.extractDate
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.time.Instant
import javax.inject.Inject

class BaseTransacrionsRepositoryImpl @Inject constructor(
    private val api: TransactionsApi,
    private val networkRepository: NetworkChekerProvider,
    private val mapper: TransactionMapper,
    private val transactionsDao: TransactionsDao,
) : BaseTransactionsRepository, TransactionsProvider {

    override suspend fun deleteTransaction(transactionId: Int): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getHistoryByPeriod(
        accounts: List<Account>,
        startDate: String,
        endDate: String,
        isIncome: Boolean,
    ): Result<List<Transaction>> = Result.execute {
        if (networkRepository.getNetworkStatus().value) {
            loadHistoryFromNetwork(
                accounts = accounts,
                startDate = startDate.extractDate(),
                endDate = endDate.extractDate(),
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
        isIncome: Boolean,
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
        isIncome: Boolean,
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
        comment: String?,
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
        comment: String?,
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
        comment: String?,
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
        comment: String?,
    ): Result<Unit> = Result.execute {
        if (networkRepository.getNetworkStatus().value) {
            editNetworkTransaction(
                transactionId = transactionId,
                accountId = accountId,
                categoryId = categoryId,
                amount = amount,
                transactionDate = transactionDate,
                comment = comment
            )
        } else {
            editLocalTransaction(
                transactionId = transactionId,
                accountId = accountId,
                categoryId = categoryId,
                amount = amount,
                transactionDate = transactionDate,
                comment = comment
            )
        }
    }

    private suspend fun editNetworkTransaction(
        transactionId: Int,
        accountId: Int,
        categoryId: Int,
        amount: String,
        transactionDate: String,
        comment: String?,
    ) {
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
        val editedTransactionDb = TransactionDbModel(
            id = transactionId,
            amount = amount.toDouble(),
            categoryId = categoryId,
            accountId = accountId,
            createdAt = transactionDate,
            comment = comment
        )

        transactionsDao.editTransaction(editedTransactionDb)
    }

    private suspend fun editLocalTransaction(
        transactionId: Int,
        accountId: Int,
        categoryId: Int,
        amount: String,
        transactionDate: String,
        comment: String?,
    ) {
        val transactionDb = transactionsDao.getTransactionById(transactionId)
        val editedTransactionDb = transactionDb.transaction.copy(
            id = transactionId,
            amount = amount.toDouble(),
            categoryId = categoryId,
            accountId = accountId,
            createdAt = transactionDate,
            comment = comment,
            updatedAt = System.currentTimeMillis(),
            syncStatus = "pending"
        )

        transactionsDao.editTransaction(editedTransactionDb)
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

        pendingTransactionsDb.forEach { transaction ->
            if (transaction.localId != null) {
                syncCreatedTransaction(transaction)
            } else {
                syncEditedTransaction(transaction)
            }
        }
    }

    private suspend fun syncCreatedTransaction(transaction: TransactionDbModel) {
        val response = createNetworkTransaction(
            accountId = transaction.accountId,
            categoryId = transaction.categoryId,
            amount = transaction.amount.toString(),
            transactionDate = transaction.createdAt,
            comment = transaction.comment
        )
        transactionsDao.deleteTransactionById(transaction.id)
        transactionsDao.insertTransaction(
            mapper.mapTransactionResponseToDbModel(
                response
            )
        )
    }

    private suspend fun syncEditedTransaction(transaction: TransactionDbModel) {
        val networkTransaction = api.getTransactionById(transaction.id)

        val networkTransactionUpdateTime =
            Instant.parse(networkTransaction.updatedAt).toEpochMilli()

        if (networkTransactionUpdateTime <= transaction.updatedAt) {
            editNetworkTransaction(
                transactionId = transaction.id,
                accountId = transaction.accountId,
                categoryId = transaction.categoryId,
                amount = transaction.amount.toString(),
                transactionDate = transaction.createdAt,
                comment = transaction.comment
            )
            transactionsDao.changeTransactionSyncStatus(
                transactionId = transaction.id
            )
        } else {
            transactionsDao.insertTransaction(mapper.mapTransactionDtoToDbModel(networkTransaction))
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
