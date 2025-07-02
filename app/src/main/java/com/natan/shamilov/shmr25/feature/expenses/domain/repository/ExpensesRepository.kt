package com.natan.shamilov.shmr25.feature.expenses.domain.repository

import com.natan.shamilov.shmr25.app.data.api.Result
import com.natan.shamilov.shmr25.common.State
import com.natan.shamilov.shmr25.feature.expenses.domain.entity.Expense
import kotlinx.coroutines.flow.StateFlow

interface ExpensesRepository {
    val completeExpensesByPeriodLoadingFlow: StateFlow<State>

    suspend fun getExpensesList(): List<Expense>

    suspend fun loadTodayExpenses(): Result<Unit>

    suspend fun loadExpensesByPeriod(
        startDate: String,
        endDate: String
    ): Result<List<Expense>>
}
