package com.natan.shamilov.shmr25.option.presentation.navigation

import com.natan.shamilov.shmr25.common.impl.domain.entity.Screen
import com.natan.shamilov.shmr25.options.R

/**
 * Определяет навигационные маршруты для функционала настроек.
 * Ответственность: Инкапсуляция логики создания и управления маршрутами
 * навигации для экранов настроек приложения.
 */
sealed class OptionsFlow(
    override val route: String,
    val title: Int? = null,
    val endIcone: Int? = null,
    val startIcone: Int? = null,
) : Screen {

    /**
     * Граф навигации настроек
     */
    data object OptionsGraph : OptionsFlow(OPTIONS_GRAPH_ROUTE)

    /**
     * Экран настроек
     */
    data object Options : OptionsFlow(
        route = OPTIONS_ROUTE,
        title = R.string.options,
        null,
        null
    )

    data object MainColor : OptionsFlow(
        route = MAIN_COLOR_ROUTE,
        title = R.string.main_color,
        startIcone = R.drawable.ic_back,
    )

    data object Sound : OptionsFlow(
        route = SOUND_ROUTE,
        title = R.string.sound,
        startIcone = R.drawable.ic_back,
    )

    data object Vibration : OptionsFlow(
        route = VIBRATION_ROUTE,
        title = R.string.vibration,
        startIcone = R.drawable.ic_back,
    )

    data object PinCode : OptionsFlow(
        route = PIN_CODE_ROUTE,
        title = R.string.password,
        startIcone = R.drawable.ic_back,
    )

    data object Sync : OptionsFlow(
        route = SYNC_ROUTE,
        title = R.string.sync,
        startIcone = R.drawable.ic_back,
    )

    data object Language : OptionsFlow(
        route = LANGUAGE_ROUTE,
        title = R.string.language,
        startIcone = R.drawable.ic_back,
    )

    data object AboutApp : OptionsFlow(
        route = ABOUT_APP_ROUTE,
        title = R.string.about_app,
        startIcone = R.drawable.ic_back,
    )
    companion object {
        const val OPTIONS_GRAPH_ROUTE = "options_graph_route"
        const val OPTIONS_ROUTE = "options_route"
        const val MAIN_COLOR_ROUTE = "main_color_route"
        const val SOUND_ROUTE = "sound_route"
        const val VIBRATION_ROUTE = "vibration_route"
        const val PIN_CODE_ROUTE = "pin_code_route"
        const val SYNC_ROUTE = "sync_route"
        const val LANGUAGE_ROUTE = "language_route"
        const val ABOUT_APP_ROUTE = "about_app_route"
    }
}
