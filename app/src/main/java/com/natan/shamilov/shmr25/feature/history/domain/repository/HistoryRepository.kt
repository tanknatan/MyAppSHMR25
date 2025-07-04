package com.natan.shamilov.shmr25.feature.history.domain.repository

import com.natan.shamilov.shmr25.common.data.model.Result
import com.natan.shamilov.shmr25.feature.history.domain.model.HistoryItem

interface HistoryRepository {
    suspend fun getHistoryByPeriod(
        startDate: String,
        endDate: String,
        isIncome: Boolean
    ): Result<List<HistoryItem>>
}
