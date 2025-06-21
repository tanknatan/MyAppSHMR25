package com.natan.shamilov.shmr25.presentation.feature.incomes.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.natan.shamilov.shmr25.presentation.feature.incomes.presentation.screen.IncomesHistoryScreen
import com.natan.shamilov.shmr25.presentation.feature.incomes.presentation.screen.IncomesTodayScreen
import com.natan.shamilov.shmr25.presentation.navigation.NavigationState


fun NavGraphBuilder.incomesGraph(navController: NavigationState) {


    navigation(
        route = IncomesFlow.IncomesGraph.route,
        startDestination = IncomesFlow.IncomesToday.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(IncomesFlow.IncomesToday.route) {

            IncomesTodayScreen(onHistoryClick = { navController.navigateSingleTopTo(IncomesFlow.IncomesHistory) })

        }

        composable(IncomesFlow.IncomesHistory.route) {
            IncomesHistoryScreen(onBackPressed = { navController.navHostController.popBackStack() })
        }


    }
}