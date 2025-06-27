package com.natan.shamilov.shmr25.presentation.feature.expenses.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.natan.shamilov.shmr25.presentation.feature.expenses.presentation.screen.ExpensesHistoryScreen
import com.natan.shamilov.shmr25.presentation.feature.expenses.presentation.screen.ExpensesTodayScreen
import com.natan.shamilov.shmr25.presentation.navigation.NavigationState

fun NavGraphBuilder.expensesGraph(navController: NavigationState) {
    navigation(
        route = ExpensesFlow.ExpensesGraph.route,
        startDestination = ExpensesFlow.ExpensesToday.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(ExpensesFlow.ExpensesToday.route) {
            ExpensesTodayScreen(
                onHistoryClick = { navController.navigateSingleTopTo(ExpensesFlow.ExpensesHistory) },
                onFABClick = { }
            )
        }
        composable(ExpensesFlow.ExpensesHistory.route) {
            ExpensesHistoryScreen(onBackClick = { navController.navHostController.popBackStack() })
        }
    }
}
