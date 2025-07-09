package com.natan.shamilov.shmr25.app.presentation.navigation

import com.natan.shamilov.shmr25.common.domain.entity.Screen as commoScreen

/**
 * Определяет все экраны приложения с их маршрутами и UI-атрибутами.
 * Ответственность: Централизованное определение всех доступных экранов приложения,
 * их маршрутов и связанных UI-элементов (заголовки, иконки).
 */
sealed class Screen(
    override val route: String,
) : commoScreen {

    /** Главный экран приложения */
    data object Main : Screen(MAIN_ROUTE)

    companion object {
        const val MAIN_ROUTE = "main"
    }
}
