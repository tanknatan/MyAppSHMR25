package com.natan.shamilov.shmr25.presentation.feature.incomes.domain.usecase

import com.natan.shamilov.shmr25.data.api.Result
import com.natan.shamilov.shmr25.domain.entity.Income
import com.natan.shamilov.shmr25.presentation.feature.incomes.domain.repository.IncomesRepository
import javax.inject.Inject

class LoadIncomesByPeriodUseCase @Inject constructor(
    private val repository: IncomesRepository
) {
    suspend operator fun invoke(
        startDate: String,
        endDate: String
    ): Result<List<Income>> = repository.loadIncomesByPeriod(startDate, endDate)
} 