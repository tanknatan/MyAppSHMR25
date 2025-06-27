package com.natan.shamilov.shmr25.feature.option.presentation.navigation

import com.natan.shamilov.shmr25.common.Screen

sealed class OptionsFlow(override val route: String) : Screen {

    data object OptionsGraph : OptionsFlow(OPTIONS_GRAPH_ROUTE)

    data object Options : OptionsFlow(OPTIONS_ROUTE)

    companion object {
        const val OPTIONS_GRAPH_ROUTE = "options_graph_route"
        const val OPTIONS_ROUTE = "options_route"
    }
}
