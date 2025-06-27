package com.natan.shamilov.shmr25.feature.categories.presentation.navigation

import com.natan.shamilov.shmr25.common.Screen

sealed class CategoriesFlow(override val route: String) : Screen {

    data object CategoriesGraph : CategoriesFlow(CATEGORIES_GRAPH_ROUTE)

    data object Categories : CategoriesFlow(CATEGORIES_ROUTE)

    companion object {
        const val CATEGORIES_GRAPH_ROUTE = "categories_graph_route"
        const val CATEGORIES_ROUTE = "categories_route"
    }
}
