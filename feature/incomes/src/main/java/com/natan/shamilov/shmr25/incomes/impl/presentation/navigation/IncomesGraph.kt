package com.natan.shamilov.shmr25.incomes.impl.presentation.navigation

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
import com.natan.shamilov.shmr25.incomes.impl.presentation.screen.addIncomes.addExpenses.AddIncomesScreen
import com.natan.shamilov.shmr25.incomes.impl.presentation.screen.editIncomes.EditIncomesScreen
import com.natan.shamilov.shmr25.feature.incomes.presentation.navigation.IncomesFlow
import com.natan.shamilov.shmr25.incomes.impl.di.IncomesComponent
import com.natan.shamilov.shmr25.incomes.impl.presentation.screen.todayIncomes.IncomesTodayScreen

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
                    onFABClick = { navHostController.navigate(IncomesFlow.AddIncome.route) },
                    onItemClick = { incomeId ->
                        navHostController.navigate(IncomesFlow.EditIncome.createRoute(incomeId.id.toString()))
                    }
                )
            }
        }

        composable(IncomesFlow.AddIncome.route) {
            CompositionLocalProvider(
                LocalViewModelFactory provides incomesComponent.viewModelFactory()
            ) {
                AddIncomesScreen(onBackPressed = { navHostController.popBackStack() })
            }
        }

        composable(
            route = "edit_income_route/{incomeId}",
            arguments = listOf(navArgument("incomeId") { type = NavType.StringType })
        ) {
            val expenseId = it.arguments?.getString("incomeId") ?: error("Missing incomeId")

            CompositionLocalProvider(
                LocalViewModelFactory provides incomesComponent.viewModelFactory()
            ) {
                EditIncomesScreen(
                    incomeId = expenseId,
                    onBackPressed = { navHostController.popBackStack() }
                )
            }
        }
    }
}
