package com.natan.shamilov.shmr25.feature.account.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.natan.shamilov.shmr25.app.presentation.navigation.NavigationState
import com.natan.shamilov.shmr25.feature.account.presentation.screen.accounts.AccountScreen
import com.natan.shamilov.shmr25.feature.account.presentation.screen.addAccount.AddAccountScreen
import com.natan.shamilov.shmr25.feature.account.presentation.screen.editAccount.EditAccountScreen

fun NavGraphBuilder.accountGraph(navController: NavigationState) {
    navigation(
        route = AccountFlow.AccountGraph.route,
        startDestination = AccountFlow.Account.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(AccountFlow.Account.route) {
            AccountScreen(
                onFABClick = {
                    navController.navigateSingleTopTo(AccountFlow.AddAccount)
                },
                onEditAccountClick = { accountId ->
                    navController.navHostController.navigate(
                        AccountFlow.EditAccount.route + "/$accountId"
                    )
                }
            )
        }

        composable(AccountFlow.AddAccount.route) {
            AddAccountScreen(onBackPressed = {
                navController.navHostController.popBackStack()
            })
        }

        composable(
            route = AccountFlow.EditAccount.route + "/{accountId}",
            arguments = listOf(navArgument("accountId") { type = NavType.StringType })
        ) { backStackEntry ->
            val accountId = backStackEntry.arguments?.getString("accountId") ?: ""
            EditAccountScreen(
                accountId = accountId,
                onBackPressed = {
                    navController.navHostController.popBackStack()
                }
            )
        }
    }
}
