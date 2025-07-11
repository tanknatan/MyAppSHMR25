package com.natan.shamilov.shmr25.incomes.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.natan.shamilov.shmr25.common.impl.domain.entity.HistoryType
import com.natan.shamilov.shmr25.common.impl.presentation.LocalViewModelFactory
import com.natan.shamilov.shmr25.feature.incomes.presentation.navigation.IncomesFlow
import com.natan.shamilov.shmr25.feature.incomes.presentation.screen.IncomesTodayScreen
import com.natan.shamilov.shmr25.incomes.di.IncomesComponent

fun NavGraphBuilder.incomesGraph(navHostController: NavHostController, incomesComponent: IncomesComponent) {
    navigation(
        route = IncomesFlow.IncomesGraph.route,
        startDestination = IncomesFlow.IncomesToday.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(IncomesFlow.IncomesToday.route) {
            CompositionLocalProvider(
                LocalViewModelFactory provides incomesComponent.viewModelFactory()
            ) {
                IncomesTodayScreen(
                    onHistoryClick = {
                        navHostController.navigate(
                            IncomesFlow.IncomesToday.createRoute(
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
}
