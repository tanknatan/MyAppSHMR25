package com.natan.shamilov.shmr25.feature.incomes.presentation.navigation

import com.natan.shamilov.shmr25.R
import com.natan.shamilov.shmr25.common.domain.entity.Screen

sealed class IncomesFlow(
    override val route: String,
    val title: Int? = null,
    val endIcone: Int? = null,
    val startIcone: Int? = null,
) : Screen {

    data object IncomesGraph : IncomesFlow(INCOMES_GRAP)

    data object IncomesToday : IncomesFlow(
        route = INCOMES_TODAY_ROUTE,
        title = R.string.incomes_today,
        R.drawable.ic_history,
        null
    )

    companion object {
        const val INCOMES_GRAP = "incomes_graph"
        const val INCOMES_TODAY_ROUTE = "incomes_today_route"
    }
}
