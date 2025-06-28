package com.natan.shamilov.shmr25.feature.history.domain.repository

import com.natan.shamilov.shmr25.app.data.api.Result
import com.natan.shamilov.shmr25.feature.expenses.domain.entity.Expense
import com.natan.shamilov.shmr25.feature.incomes.domain.entity.Income

interface HistoryRepository {
    suspend fun getExpensesByPeriod(startDate: String, endDate: String): Result<List<Expense>>
    suspend fun getIncomesByPeriod(startDate: String, endDate: String): Result<List<Income>>
} 