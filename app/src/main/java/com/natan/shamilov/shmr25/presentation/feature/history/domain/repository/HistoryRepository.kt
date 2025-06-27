package com.natan.shamilov.shmr25.presentation.feature.history.domain.repository

import com.natan.shamilov.shmr25.data.api.Result
import com.natan.shamilov.shmr25.domain.entity.Expense
import com.natan.shamilov.shmr25.domain.entity.Income

interface HistoryRepository {
    suspend fun getExpensesByPeriod(startDate: String, endDate: String): Result<List<Expense>>
    suspend fun getIncomesByPeriod(startDate: String, endDate: String): Result<List<Income>>
} 