package com.natan.shamilov.shmr25.feature.account.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.natan.shamilov.shmr25.app.navigation.NavigationState
import com.natan.shamilov.shmr25.feature.account.presentation.screen.accounts.AccountScreen
import com.natan.shamilov.shmr25.feature.account.presentation.screen.addAccount.AddAccountScreen

fun NavGraphBuilder.accountGraph(navController: NavigationState) {
    navigation(
        route = AccountFlow.AccountGraph.route,
        startDestination = AccountFlow.Account.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(AccountFlow.Account.route) {
            AccountScreen(onFABClick = {
                navController.navigateSingleTopTo(AccountFlow.AddAccount)
            })
        }

        composable(AccountFlow.AddAccount.route) {
            AddAccountScreen(onBackPressed = {
                navController.navHostController.popBackStack()
            })
        }
    }
}
