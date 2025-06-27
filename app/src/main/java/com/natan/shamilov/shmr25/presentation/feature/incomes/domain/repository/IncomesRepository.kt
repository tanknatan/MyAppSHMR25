package com.natan.shamilov.shmr25.presentation.feature.incomes.domain.repository

import com.natan.shamilov.shmr25.data.api.Result
import com.natan.shamilov.shmr25.domain.entity.Income

interface IncomesRepository {
    suspend fun getIncomesList(): List<Income>
    
    suspend fun loadTodayIncomes(): Result<Unit>
    
    suspend fun loadIncomesByPeriod(
        startDate: String,
        endDate: String
    ): Result<List<Income>>
} 