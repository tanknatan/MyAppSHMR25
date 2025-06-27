package com.natan.shamilov.shmr25.presentation.feature.expenses.presentation.navigation

import com.natan.shamilov.shmr25.common.Screen

sealed class ExpensesFlow(override val route: String) : Screen {

    data object ExpensesGraph : ExpensesFlow(EXPENSES_GRAP)

    data object ExpensesToday : ExpensesFlow(EXPENSESTODAY_ROUTE)

    data object MyExpenses : ExpensesFlow(MYEXPENSES_ROUTE)

    companion object {
        const val EXPENSES_GRAP = "expenses_graph"
        const val EXPENSESTODAY_ROUTE = "expenses_today_route"
        const val MYEXPENSES_ROUTE = "my_expenses_route"
    }
}
