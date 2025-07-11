package com.natan.shamilov.shmr25.feature.incomes.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.natan.shamilov.shmr25.app.presentation.navigation.NavigationState
import com.natan.shamilov.shmr25.feature.history.domain.HistoryType
import com.natan.shamilov.shmr25.feature.history.presentation.navigation.HistoryFlow
import com.natan.shamilov.shmr25.feature.incomes.presentation.screen.IncomesTodayScreen

fun NavGraphBuilder.incomesGraph(navController: NavigationState) {
    navigation(
        route = IncomesFlow.IncomesGraph.route,
        startDestination = IncomesFlow.IncomesToday.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(IncomesFlow.IncomesToday.route) {
            IncomesTodayScreen(
                onHistoryClick = {
                    navController.navHostController.navigate(
                        HistoryFlow.History.createRoute(
                            type = HistoryType.INCOME,
                            from = IncomesFlow.IncomesGraph.route
                        )
                    )
                },
                onFABClick = { }
            )
        }
    }
}
