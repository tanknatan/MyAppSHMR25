package com.natan.shamilov.shmr25.feature.history.data.repository

import com.natan.shamilov.shmr25.common.api.AccountProvider
import com.natan.shamilov.shmr25.common.impl.data.api.TransactionsApi
import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.feature.history.data.mapper.HistoryMapper
import com.natan.shamilov.shmr25.feature.history.domain.model.HistoryItem
import com.natan.shamilov.shmr25.feature.history.domain.repository.HistoryRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val api: TransactionsApi,
    private val historyMapper: HistoryMapper,
    private val accountProvider: AccountProvider,
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

    companion object {
        private const val START_OF_DATA_IN_RESPONSE = 0
        private const val END_OF_DATA_IN_RESPONSE = 10
    }
}
