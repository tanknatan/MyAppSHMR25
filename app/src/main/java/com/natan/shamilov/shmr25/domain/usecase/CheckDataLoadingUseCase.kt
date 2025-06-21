package com.natan.shamilov.shmr25.domain.usecase

import com.natan.shamilov.shmr25.domain.FinanceRepository
import javax.inject.Inject

class CheckAccAndTransactionDataLoadingUseCase @Inject constructor(
    private val repository: FinanceRepository
) {
     operator fun invoke() = repository.completeAccAndTransactionDataLoadingFlow
}