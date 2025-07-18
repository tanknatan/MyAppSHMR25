package com.natan.shamilov.shmr25.feature.expenses.presentation.navigation

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
import com.natan.shamilov.shmr25.expenses.impl.di.ExpensesComponent
import com.natan.shamilov.shmr25.expenses.impl.presentation.navigation.ExpensesFlow
import com.natan.shamilov.shmr25.expenses.impl.presentation.screen.addExpenses.AddExpensesScreen
import com.natan.shamilov.shmr25.expenses.impl.presentation.screen.editExpenses.EditExpensesScreen
import com.natan.shamilov.shmr25.expenses.impl.presentation.screen.todayExpenses.ExpensesTodayScreen

fun NavGraphBuilder.expensesGraph(navHostController: NavHostController, expensesComponent: ExpensesComponent) {
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
                    onFABClick = { navHostController.navigate(ExpensesFlow.AddExpense.route) },
                    onItemClick = { expenseId ->
                        navHostController.navigate(ExpensesFlow.EditExpense.createRoute(expenseId.id.toString()))
                    }
                )
            }
        }
        composable(ExpensesFlow.AddExpense.route) {
            CompositionLocalProvider(
                LocalViewModelFactory provides expensesComponent.viewModelFactory()
            ) {
                AddExpensesScreen(onBackPressed = { navHostController.popBackStack() })
            }
        }
        composable(
            route = "edit_expense_route/{expenseId}",
            arguments = listOf(navArgument("expenseId") { type = NavType.StringType })
        ) {
            val expenseId = it.arguments?.getString("expenseId") ?: error("Missing expenseId")

            CompositionLocalProvider(
                LocalViewModelFactory provides expensesComponent.viewModelFactory()
            ) {
                EditExpensesScreen(
                    expenseId = expenseId,
                    onBackPressed = { navHostController.popBackStack() }
                )
            }
        }
    }
}

