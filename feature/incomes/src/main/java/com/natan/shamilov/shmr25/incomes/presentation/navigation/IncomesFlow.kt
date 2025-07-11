package com.natan.shamilov.shmr25.feature.incomes.presentation.navigation

import com.natan.shamilov.shmr25.common.impl.domain.entity.HistoryType
import com.natan.shamilov.shmr25.common.impl.domain.entity.Screen
import com.natan.shamilov.shmr25.incomes.R

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
    ) {
        fun createRoute(type: HistoryType, from: String) = "history/${type.name}?from=$from"
    }

    companion object {
        const val INCOMES_GRAP = "incomes_graph"
        const val INCOMES_TODAY_ROUTE = "incomes_today_route"
    }
}
