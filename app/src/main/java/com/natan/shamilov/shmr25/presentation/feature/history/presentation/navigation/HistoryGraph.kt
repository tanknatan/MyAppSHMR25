package com.natan.shamilov.shmr25.presentation.feature.history.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.natan.shamilov.shmr25.presentation.feature.history.domain.HistoryType
import com.natan.shamilov.shmr25.presentation.feature.history.presentation.screen.HistoryScreen
import com.natan.shamilov.shmr25.presentation.navigation.NavigationState

fun NavGraphBuilder.historyGraph(navController: NavigationState) {
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
        
        HistoryScreen(
            type = type,
            onBackClick = { navController.navHostController.popBackStack() }
        )
    }
}
