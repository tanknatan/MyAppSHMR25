package com.natan.shamilov.shmr25.feature.account.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.natan.shamilov.shmr25.account.impl.di.AccountsComponent
import com.natan.shamilov.shmr25.account.impl.presentation.screen.accounts.AccountScreen
import com.natan.shamilov.shmr25.common.impl.presentation.LocalViewModelFactory
import com.natan.shamilov.shmr25.account.impl.presentation.screen.addAccount.AddAccountScreen
import com.natan.shamilov.shmr25.account.impl.presentation.screen.editAccount.EditAccountScreen

fun NavGraphBuilder.accountGraph(navHostController: NavHostController, accountsComponent: AccountsComponent) {
    navigation(
        route = AccountFlow.AccountGraph.route,
        startDestination = AccountFlow.Account.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
    ) {
        composable(AccountFlow.Account.route) {
            CompositionLocalProvider(
                LocalViewModelFactory provides accountsComponent.viewModelFactory()
            ) {
                AccountScreen(
                    onFABClick = {
                        navHostController.navigate(AccountFlow.AddAccount.route)
                    },
                    onEditAccountClick = { accountId ->
                        navHostController.navigate(
                            AccountFlow.EditAccount.route
                        )
                    }
                )
            }
        }

        composable(AccountFlow.AddAccount.route) {
            CompositionLocalProvider(
                LocalViewModelFactory provides accountsComponent.viewModelFactory()
            ) {
                AddAccountScreen(onBackPressed = {
                    navHostController.popBackStack()
                })
            }
        }

        composable(AccountFlow.EditAccount.route) { backStackEntry ->
            CompositionLocalProvider(
                LocalViewModelFactory provides accountsComponent.viewModelFactory()
            ) {
                EditAccountScreen(
                    onBackPressed = {
                        navHostController.popBackStack()
                    }
                )
            }
        }
    }
}
