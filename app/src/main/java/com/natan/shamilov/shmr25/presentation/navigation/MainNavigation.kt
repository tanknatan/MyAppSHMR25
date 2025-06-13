package com.natan.shamilov.shmr25.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun MainNavigation(
    navController: NavHostController,
    expensesScreenContent: @Composable () -> Unit,
    incomesScreenContent: @Composable () -> Unit,
    accountScreenContent: @Composable () -> Unit,
    categoriesScreenContent: @Composable () -> Unit,
    optionsScreenContent: @Composable () -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Expenses.route,
        enterTransition =  { EnterTransition.None },
        exitTransition = { ExitTransition.None },
    ) {
        composable(Screen.Expenses.route) {
            expensesScreenContent()
        }
        composable(Screen.Incomes.route) {
            incomesScreenContent()
        }
        composable(Screen.Account.route) {
            accountScreenContent()
        }
        composable(Screen.Categories.route) {
            categoriesScreenContent()
        }
        composable(Screen.Options.route) {
            optionsScreenContent()
        }

    }

}