package com.natan.shamilov.shmr25.incomes.impl.domain.usecase

import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.common.impl.domain.entity.Transaction
import com.natan.shamilov.shmr25.incomes.impl.domain.repository.IncomesRepository
import javax.inject.Inject

class GetIncomesListUseCase @Inject constructor(
    private val repository: IncomesRepository,
) {
    suspend operator fun invoke(): Result<List<Transaction>> = repository.getTransactionList()
} 
