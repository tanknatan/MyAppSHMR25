package com.natan.shamilov.shmr25.presentation.feature.history.presentation.navigation

import com.natan.shamilov.shmr25.common.Screen
import com.natan.shamilov.shmr25.presentation.feature.history.domain.HistoryType

sealed class HistoryFlow(override val route: String) : Screen {
    companion object {
        const val TYPE_KEY = "type"
        const val FROM_KEY = "from"
    }

    data object History : HistoryFlow("history/{$TYPE_KEY}?from={$FROM_KEY}") {
        fun createRoute(type: HistoryType, from: String) = "history/${type.name}?from=$from"
    }
}
