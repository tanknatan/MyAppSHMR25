package com.natan.shamilov.shmr25.feature.categories.presentation.navigation

import com.natan.shamilov.shmr25.common.domain.entity.Screen

/**
 * Определяет навигационные маршруты для функционала категорий.
 * Ответственность: Инкапсуляция логики создания и управления маршрутами
 * навигации для экранов категорий.
 */
sealed class CategoriesFlow(override val route: String) : Screen {

    /**
     * Граф навигации категорий
     */
    data object CategoriesGraph : CategoriesFlow(CATEGORIES_GRAPH_ROUTE)

    /**
     * Экран списка категорий
     */
    data object Categories : CategoriesFlow(CATEGORIES_ROUTE)

    companion object {
        const val CATEGORIES_GRAPH_ROUTE = "categories_graph_route"
        const val CATEGORIES_ROUTE = "categories_route"
    }
}
