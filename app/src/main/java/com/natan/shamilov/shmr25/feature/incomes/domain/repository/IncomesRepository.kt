package com.natan.shamilov.shmr25.feature.incomes.domain.repository

import com.natan.shamilov.shmr25.common.data.model.Result
import com.natan.shamilov.shmr25.feature.incomes.domain.entity.Income

interface IncomesRepository {
    suspend fun getIncomesList(): List<Income>
    
    suspend fun loadTodayIncomes(): Result<Unit>
    
    suspend fun loadIncomesByPeriod(
        startDate: String,
        endDate: String
    ): Result<List<Income>>
} 