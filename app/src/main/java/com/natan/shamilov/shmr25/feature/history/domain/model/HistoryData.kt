package com.natan.shamilov.shmr25.feature.history.domain.model

import com.natan.shamilov.shmr25.feature.expenses.domain.entity.Expense
import com.natan.shamilov.shmr25.feature.incomes.domain.entity.Income

/**
 * Модель данных для экрана истории.
 * Ответственность: Агрегация данных о доходах и расходах за период,
 * включая списки транзакций и итоговые суммы. Также предоставляет
 * вычисление баланса на основе общих сумм.
 */
data class HistoryData(
    val expenses: List<Expense>,
    val incomes: List<Income>,
    val totalExpenses: Double,
    val totalIncomes: Double
) {
    /**
     * Вычисляет текущий баланс как разницу между доходами и расходами
     */
    val balance: Double
        get() = totalIncomes - totalExpenses
}
