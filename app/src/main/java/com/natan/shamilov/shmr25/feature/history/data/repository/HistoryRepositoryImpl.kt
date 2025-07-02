package com.natan.shamilov.shmr25.feature.history.data.repository

import com.natan.shamilov.shmr25.app.data.api.Result
import com.natan.shamilov.shmr25.feature.account.domain.entity.Account
import com.natan.shamilov.shmr25.feature.history.data.api.HistoryApi
import com.natan.shamilov.shmr25.feature.history.data.mapper.HistoryMapper
import com.natan.shamilov.shmr25.feature.history.domain.model.HistoryItem
import com.natan.shamilov.shmr25.feature.history.domain.repository.HistoryRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val api: HistoryApi,
    private val historyMapper: HistoryMapper,
) : HistoryRepository {

    override suspend fun getHistoryByPeriod(
        accounts: List<Account>,
        startDate: String,
        endDate: String,
        isIncome: Boolean
    ): Result<List<HistoryItem>> = Result.execute {
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
            .sortedByDescending { it.createdAt }
            .map { historyMapper.mapTransactionDtoToHistoryItem(it) }
    }
}
