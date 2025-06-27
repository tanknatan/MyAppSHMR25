package com.natan.shamilov.shmr25.presentation.feature.history.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.natan.shamilov.shmr25.presentation.feature.expenses.presentation.screen.ExpensesHistoryScreen
import com.natan.shamilov.shmr25.presentation.navigation.NavigationState

fun NavGraphBuilder.historyGraph(navController: NavigationState) {
    navigation(
        route = HistoryFlow.HistoryGraph.route,
        startDestination = HistoryFlow.History.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(HistoryFlow.History.route) {
            ExpensesHistoryScreen(onBackClick = { navController.navHostController.popBackStack() })
        }
    }
}
