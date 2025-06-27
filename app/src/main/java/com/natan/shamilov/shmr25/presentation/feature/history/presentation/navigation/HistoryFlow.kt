package com.natan.shamilov.shmr25.presentation.feature.history.presentation.navigation

import com.natan.shamilov.shmr25.common.Screen

sealed class HistoryFlow(override val route: String) : Screen {

    data object HistoryGraph : HistoryFlow(GRAP)

    data object History : HistoryFlow(HISTORY_ROUTE)

    companion object {
        const val GRAP = "history_graph"
        const val HISTORY_ROUTE = "history_route"
    }
}
