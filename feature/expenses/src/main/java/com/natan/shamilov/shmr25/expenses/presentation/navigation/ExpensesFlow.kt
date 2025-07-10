package com.natan.shamilov.shmr25.feature.expenses.presentation.navigation

import com.natan.shamilov.shmr25.R
import com.natan.shamilov.shmr25.common.domain.entity.Screen

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
    )

    companion object {
        const val EXPENSES_GRAP = "expenses_graph"
        const val EXPENSESTODAY_ROUTE = "expenses_today_route"
    }
}
