package com.natan.shamilov.shmr25.feature.incomes.domain.repository

import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.feature.incomes.domain.entity.Income

interface IncomesRepository {
    suspend fun getIncomesList(): List<Income>

    suspend fun loadTodayIncomes(): com.natan.shamilov.shmr25.common.impl.data.model.Result<Unit>

    suspend fun loadIncomesByPeriod(
        startDate: String,
        endDate: String
    ): com.natan.shamilov.shmr25.common.impl.data.model.Result<List<Income>>
}
