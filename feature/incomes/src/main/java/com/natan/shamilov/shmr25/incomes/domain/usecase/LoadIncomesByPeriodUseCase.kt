package com.natan.shamilov.shmr25.feature.incomes.domain.usecase

import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.feature.incomes.domain.entity.Income
import com.natan.shamilov.shmr25.feature.incomes.domain.repository.IncomesRepository
import javax.inject.Inject

class LoadIncomesByPeriodUseCase @Inject constructor(
    private val repository: IncomesRepository
) {
    suspend operator fun invoke(
        startDate: String,
        endDate: String
    ): com.natan.shamilov.shmr25.common.impl.data.model.Result<List<Income>> = repository.loadIncomesByPeriod(startDate, endDate)
}
