package com.natan.shamilov.shmr25.history.impl.presentation.navigation

import com.natan.shamilov.shmr25.common.impl.domain.entity.Screen
import com.natan.shamilov.shmr25.history.R

/**
 * Определяет навигационные маршруты для функционала истории.
 * Ответственность: Инкапсуляция логики создания и управления маршрутами
 * навигации для экранов истории, включая параметры типа истории и источника перехода.
 */
sealed class HistoryFlow(
    override val route: String,
    val title: Int? = null,
    val endIcone: Int? = null,
    val startIcone: Int? = null,
) : Screen {
    companion object {
        const val GRAPH_ROUTE = "history_graph_route"
        const val TYPE_KEY = "type"
        const val FROM_KEY = "from"
    }

    data object HistoryGraph : HistoryFlow(GRAPH_ROUTE)

    /**
     * Экран истории с параметрами типа и источника
     */
    data object History : HistoryFlow("history/{$TYPE_KEY}?from={$FROM_KEY}")

    data object EditHistory : HistoryFlow(
        route = "edit_history_route/{historyId}?from={$FROM_KEY}",
        endIcone = R.drawable.ic_accept,
        startIcone = R.drawable.ic_close
    ) {
        fun createRoute(historyId: String, from: String): String =
            "edit_history_route/$historyId?from=$from"
    }
}
