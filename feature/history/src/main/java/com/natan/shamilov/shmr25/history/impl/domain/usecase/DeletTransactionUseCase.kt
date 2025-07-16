package com.natan.shamilov.shmr25.history.impl.domain.usecase

import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.history.impl.domain.repository.HistoryRepository
import javax.inject.Inject

class DeletTransactionUseCase @Inject constructor(
    private val repository: HistoryRepository,
) {
    suspend operator fun invoke(id: Int): Result<Unit> =
        repository.deleteTransaction(id)
}