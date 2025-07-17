package com.natan.shamilov.shmr25.history.impl.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.natan.shamilov.shmr25.common.impl.domain.entity.HistoryType
import com.natan.shamilov.shmr25.common.impl.presentation.LocalViewModelFactory
import com.natan.shamilov.shmr25.history.impl.di.HistoryComponent
import com.natan.shamilov.shmr25.history.impl.presentation.screen.analysis.AnalysisScreen
import com.natan.shamilov.shmr25.history.impl.presentation.screen.editHistory.EditHistoryScreen
import com.natan.shamilov.shmr25.history.impl.presentation.screen.history.HistoryScreen

fun NavGraphBuilder.historyGraph(navHostController: NavHostController, historyComponent: HistoryComponent) {
    navigation(
        route = HistoryFlow.HistoryGraph.route,
        startDestination = HistoryFlow.History.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
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
        ) { backStackEntry ->
            val type = backStackEntry.arguments?.getString(HistoryFlow.TYPE_KEY)?.let {
                HistoryType.valueOf(it)
            } ?: HistoryType.EXPENSE
            val from = backStackEntry.arguments?.getString(HistoryFlow.FROM_KEY) ?: ""
            CompositionLocalProvider(
                LocalViewModelFactory provides historyComponent.viewModelFactory()
            ) {
                HistoryScreen(
                    type = type,
                    onBackClick = { navHostController.popBackStack() },
                    onItemClick = { historyId ->
                        navHostController.navigate(HistoryFlow.EditHistory.createRoute(historyId.id.toString(), from))
                    },
                    onNavigateClick = { navHostController.navigate(HistoryFlow.Analysis.createRoute(type, from)) },
                )
            }
        }

        composable(
            route = HistoryFlow.EditHistory.route,
            arguments = listOf(
                navArgument("historyId") { type = NavType.StringType },
                navArgument(HistoryFlow.FROM_KEY) {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->
            val historyId = backStackEntry.arguments?.getString("historyId") ?: error("Missing historyId")

            CompositionLocalProvider(
                LocalViewModelFactory provides historyComponent.viewModelFactory()
            ) {
                EditHistoryScreen(
                    historyId = historyId,
                    onBackPressed = { navHostController.popBackStack() },
                )
            }
        }

        composable(
            route = HistoryFlow.Analysis.route,
            arguments = listOf(
                navArgument("type") {
                    type = NavType.StringType
                    defaultValue = HistoryType.EXPENSE.name
                },
                navArgument("from") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->
            val type = backStackEntry.arguments?.getString("type")?.let {
                HistoryType.valueOf(it)
            } ?: HistoryType.EXPENSE

            CompositionLocalProvider(
                LocalViewModelFactory provides historyComponent.viewModelFactory()
            ) {
                AnalysisScreen(
                    type = type,
                    onBackClick = { navHostController.popBackStack() }
                )
            }
        }
    }
}
