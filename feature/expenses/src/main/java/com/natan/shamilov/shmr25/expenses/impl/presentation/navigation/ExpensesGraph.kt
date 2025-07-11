package com.natan.shamilov.shmr25.feature.expenses.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.natan.shamilov.shmr25.common.impl.domain.entity.HistoryType
import com.natan.shamilov.shmr25.common.impl.presentation.LocalViewModelFactory
import com.natan.shamilov.shmr25.expenses.impl.di.ExpensesComponent
import com.natan.shamilov.shmr25.expenses.impl.presentation.screen.ExpensesTodayScreen

fun NavGraphBuilder.expensesGraph(navHostController: NavHostController,expensesComponent: ExpensesComponent) {
    navigation(
        route = ExpensesFlow.ExpensesGraph.route,
        startDestination = ExpensesFlow.ExpensesToday.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(ExpensesFlow.ExpensesToday.route) {
            CompositionLocalProvider(
                LocalViewModelFactory provides expensesComponent.viewModelFactory()
            ) {
                ExpensesTodayScreen(
                    onHistoryClick = {
                        navHostController.navigate(
                            ExpensesFlow.ExpensesToday.createRoute(
                                type = HistoryType.EXPENSE,
                                from = ExpensesFlow.ExpensesGraph.route
                            )
                        )
                    },
                    onFABClick = { }
                )
            }
        }
    }
}

