package com.natan.shamilov.shmr25.feature.history.domain.model

import com.natan.shamilov.shmr25.feature.expenses.domain.entity.Expense
import com.natan.shamilov.shmr25.feature.incomes.domain.entity.Income

data class HistoryData(
    val expenses: List<Expense>,
    val incomes: List<Income>,
    val totalExpenses: Double,
    val totalIncomes: Double
) {
    val balance: Double
        get() = totalIncomes - totalExpenses
} 