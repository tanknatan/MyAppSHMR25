package com.natan.shamilov.shmr25.presentation.feature.incomes.presentation.navigation

import com.natan.shamilov.shmr25.common.Screen

sealed class IncomesFlow(override val route: String) : Screen {

    data object IncomesGraph : IncomesFlow(INCOMES_GRAP)

    data object IncomesToday : IncomesFlow(INCOMES_TODAY_ROUTE)

    data object IncomesHistory : IncomesFlow(INCOMESHISTORY_ROUTE)

    data object MyIncomes : IncomesFlow(MY_INCOMES_ROUTE)

    companion object {
        const val INCOMES_GRAP = "incomes_graph"
        const val INCOMES_TODAY_ROUTE = "incomes_today_route"
        const val INCOMESHISTORY_ROUTE = "incomes_history_route"
        const val MY_INCOMES_ROUTE = "my_incomes_route"
    }
}
