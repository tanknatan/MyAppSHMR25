package com.natan.shamilov.shmr25.incomes.impl.domain.usecase

import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.incomes.impl.domain.repository.IncomesRepository

import javax.inject.Inject

class DeletTransactionUseCase @Inject constructor(
    private val repository: IncomesRepository,
) {
    suspend operator fun invoke(id: Int): Result<Unit> =
        repository.deleteTransaction(id)
}