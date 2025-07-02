package com.natan.shamilov.shmr25.feature.history.domain.usecase

import com.natan.shamilov.shmr25.app.data.api.Result
import com.natan.shamilov.shmr25.feature.account.domain.entity.Account
import com.natan.shamilov.shmr25.feature.history.domain.model.HistoryItem
import com.natan.shamilov.shmr25.feature.history.domain.repository.HistoryRepository
import javax.inject.Inject

class GetHistoryByPeriodUseCase @Inject constructor(
    private val repository: HistoryRepository
) {
    suspend operator fun invoke(
        accounts: List<Account>,
        startDate: String,
        endDate: String,
        isIncome: Boolean
    ): Result<List<HistoryItem>> {
        return repository.getHistoryByPeriod(
            accounts = accounts,
            startDate = startDate,
            endDate = endDate,
            isIncome = isIncome
        )
    }
}
