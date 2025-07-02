package com.natan.shamilov.shmr25.feature.history.domain.repository

import com.natan.shamilov.shmr25.app.data.api.Result
import com.natan.shamilov.shmr25.feature.account.domain.entity.Account
import com.natan.shamilov.shmr25.feature.history.domain.model.HistoryItem

interface HistoryRepository {
    suspend fun getHistoryByPeriod(
        accounts: List<Account>,
        startDate: String,
        endDate: String,
        isIncome: Boolean
    ): Result<List<HistoryItem>>
}
