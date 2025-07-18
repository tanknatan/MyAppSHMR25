package com.natan.shamilov.shmr25.history.impl.domain.usecase

import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.common.impl.domain.entity.Transaction
import com.natan.shamilov.shmr25.history.impl.domain.repository.HistoryRepository
import javax.inject.Inject

class GetHistoryByPeriodUseCase @Inject constructor(
    private val repository: HistoryRepository
) {
    suspend operator fun invoke(
        startDate: String,
        endDate: String,
        isIncome: Boolean
    ): Result<List<Transaction>> {
        return repository.getTransactionList(
            startDate = startDate,
            endDate = endDate,
            isIncome = isIncome
        )
    }
}
