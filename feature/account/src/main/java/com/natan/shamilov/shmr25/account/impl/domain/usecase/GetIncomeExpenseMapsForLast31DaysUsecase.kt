package com.natan.shamilov.shmr25.account.impl.domain.usecase

import com.natan.shamilov.shmr25.account.impl.domain.repository.AccountRepository
import javax.inject.Inject

class GetIncomeExpenseMapsForLast31DaysUsecase @Inject constructor(
    private val repository: AccountRepository,
) {
    suspend operator fun invoke(): Pair<Map<String, Double>, Map<String, Double>> =
        repository.getIncomeExpenseMapsForLast31Days()
}