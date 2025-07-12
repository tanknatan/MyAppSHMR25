package com.natan.shamilov.shmr25.feature.expenses.presentation.navigation

import com.natan.shamilov.shmr25.common.impl.domain.entity.HistoryType
import com.natan.shamilov.shmr25.common.impl.domain.entity.Screen
import com.natan.shamilov.shmr25.expenses.R

sealed class ExpensesFlow(
    override val route: String,
    val title: Int? = null,
    val endIcone: Int? = null,
    val startIcone: Int? = null,
) : Screen {

    data object ExpensesGraph : ExpensesFlow(EXPENSES_GRAP)

    data object ExpensesToday : ExpensesFlow(
        route = EXPENSESTODAY_ROUTE,
        title = R.string.expenses_today,
        R.drawable.ic_history,
        null
    ) {
        fun createRoute(type: HistoryType, from: String) = "history/${type.name}?from=$from"
    }

    data object AddExpense : ExpensesFlow(
        route = ADD_EXPENSE_ROUTE,
        title = R.string.my_expenses,
        R.drawable.ic_accept,
        R.drawable.ic_close
    )

    data object EditExpense : ExpensesFlow(
        route = "edit_expense_route/{expenseId}",
        title = R.string.my_expenses,
        R.drawable.ic_accept,
        R.drawable.ic_close
    ) {
        fun createRoute(expenseId: String) = "edit_expense_route/$expenseId"
    }

    companion object {
        const val EXPENSES_GRAP = "expenses_graph"
        const val EXPENSESTODAY_ROUTE = "expenses_today_route"
        const val ADD_EXPENSE_ROUTE = "add_expense_route"
    }
}
