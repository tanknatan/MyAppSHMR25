package com.natan.shamilov.shmr25.feature.option.presentation.navigation

import com.natan.shamilov.shmr25.common.Screen

/**
 * Определяет навигационные маршруты для функционала настроек.
 * Ответственность: Инкапсуляция логики создания и управления маршрутами
 * навигации для экранов настроек приложения.
 */
sealed class OptionsFlow(override val route: String) : Screen {

    /**
     * Граф навигации настроек
     */
    data object OptionsGraph : OptionsFlow(OPTIONS_GRAPH_ROUTE)

    /**
     * Экран настроек
     */
    data object Options : OptionsFlow(OPTIONS_ROUTE)

    companion object {
        const val OPTIONS_GRAPH_ROUTE = "options_graph_route"
        const val OPTIONS_ROUTE = "options_route"
    }
}
