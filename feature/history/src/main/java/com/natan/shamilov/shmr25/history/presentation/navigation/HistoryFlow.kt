package com.natan.shamilov.shmr25.feature.history.presentation.navigation

import com.natan.shamilov.shmr25.common.impl.domain.entity.Screen

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
        const val TYPE_KEY = "type"
        const val FROM_KEY = "from"
    }

    /**
     * Экран истории с параметрами типа и источника
     */
    data object History : HistoryFlow("history/{$TYPE_KEY}?from={$FROM_KEY}")
}
