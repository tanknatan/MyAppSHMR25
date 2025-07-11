package com.natan.shamilov.shmr25.feature.history.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.natan.shamilov.shmr25.common.impl.domain.entity.HistoryType
import com.natan.shamilov.shmr25.common.impl.presentation.LocalViewModelFactory
import com.natan.shamilov.shmr25.feature.history.presentation.screen.HistoryScreen
import com.natan.shamilov.shmr25.history.di.HistoryComponent

fun NavGraphBuilder.historyGraph(navHostController: NavHostController, historyComponent: HistoryComponent) {
    composable(
        route = HistoryFlow.History.route,
        arguments = listOf(
            navArgument(HistoryFlow.TYPE_KEY) {
                type = NavType.StringType
            },
            navArgument(HistoryFlow.FROM_KEY) {
                type = NavType.StringType
                defaultValue = ""
            }
        ),
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) { backStackEntry ->
        val type = backStackEntry.arguments?.getString(HistoryFlow.TYPE_KEY)?.let {
            HistoryType.valueOf(it)
        } ?: HistoryType.EXPENSE
        CompositionLocalProvider(
            LocalViewModelFactory provides historyComponent.viewModelFactory()
        ) {
            HistoryScreen(
                type = type,
                onBackClick = { navHostController.popBackStack() }
            )
        }
    }
}
