package com.natan.shamilov.shmr25.presentation.feature.incomes.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.natan.shamilov.shmr25.presentation.feature.incomes.presentation.screen.IncomesHistoryScreen
import com.natan.shamilov.shmr25.presentation.feature.incomes.presentation.screen.IncomesTodayScreen


fun NavGraphBuilder.incomesGraph(navController: NavController) {


    navigation(
        route = IncomesFlow.IncomesGraph.route,
        startDestination = IncomesFlow.IncomesToday.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(IncomesFlow.IncomesToday.route) {

            IncomesTodayScreen()

        }

        composable(IncomesFlow.IncomesHistory.route) {
            IncomesHistoryScreen()
        }


    }
}