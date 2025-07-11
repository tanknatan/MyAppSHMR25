package com.natan.shamilov.shmr25.feature.history.domain.usecase

import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.feature.history.domain.model.HistoryItem
import com.natan.shamilov.shmr25.feature.history.domain.repository.HistoryRepository
import javax.inject.Inject

class GetHistoryByPeriodUseCase @Inject constructor(
    private val repository: HistoryRepository
) {
    suspend operator fun invoke(
        startDate: String,
        endDate: String,
        isIncome: Boolean
    ): com.natan.shamilov.shmr25.common.impl.data.model.Result<List<HistoryItem>> {
        return repository.getHistoryByPeriod(
            startDate = startDate,
            endDate = endDate,
            isIncome = isIncome
        )
    }
}
