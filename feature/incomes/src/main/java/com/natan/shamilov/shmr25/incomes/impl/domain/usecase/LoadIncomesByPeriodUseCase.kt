package com.natan.shamilov.shmr25.incomes.impl.domain.usecase

import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.incomes.impl.domain.entity.Income
import com.natan.shamilov.shmr25.incomes.impl.domain.repository.IncomesRepository
import javax.inject.Inject

class LoadIncomesByPeriodUseCase @Inject constructor(
    private val repository: IncomesRepository
) {
    suspend operator fun invoke(
        startDate: String,
        endDate: String
    ): Result<List<Income>> = repository.loadIncomesByPeriod(startDate, endDate)
}
