package com.natan.shamilov.shmr25.presentation

import android.util.Log
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.natan.shamilov.shmr25.presentation.feature.account.presentation.navigation.accountGraph
import com.natan.shamilov.shmr25.presentation.feature.categories.presentation.screen.CategoriesScreen
import com.natan.shamilov.shmr25.presentation.feature.expenses.presentation.navigation.ExpensesFlow
import com.natan.shamilov.shmr25.presentation.feature.expenses.presentation.navigation.expensesGraph
import com.natan.shamilov.shmr25.presentation.feature.incomes.presentation.navigation.incomesGraph
import com.natan.shamilov.shmr25.presentation.feature.option.OptionScreen
import com.natan.shamilov.shmr25.presentation.navigation.NavigationItem
import com.natan.shamilov.shmr25.presentation.navigation.Screen
import com.natan.shamilov.shmr25.presentation.navigation.rememberNavigationState
import com.natan.shamilov.shmr25.ui.theme.BottomBarBackground
import com.natan.shamilov.shmr25.ui.theme.rodotoFont

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navigationState = rememberNavigationState()
    val navBackStackEntry = navigationState.navHostController.currentBackStackEntryAsState()

    val navigationList = listOf(
        NavigationItem.Expenses,
        NavigationItem.Incomes,
        NavigationItem.Account,
        NavigationItem.Categories,
        NavigationItem.Options
    )
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                modifier = Modifier.navigationBarsPadding(),
                containerColor = BottomBarBackground
            ) {
                navigationList.forEach { item ->
                    val selected =
                        navBackStackEntry.value?.destination?.hierarchy?.any { destination ->
                            item.screen.route == destination.route
                        } ?: false
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            val result = navBackStackEntry.value?.destination?.hierarchy?.map {
                                it.route
                            }
                            if (!selected) navigationState.bottomNavigate(item.screen)

                            if (result != null) {
                                Log.d("Navigation", result.toList().toString())
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(item.iconId),
                                contentDescription = null,

                                )
                        },
                        label = {
                            Text(
                                text = stringResource(item.label),
                                style = MaterialTheme.typography.labelMedium,
                                fontFamily = rodotoFont,
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.secondary,
                        )
                    )
                }
            }
        },
        contentWindowInsets = WindowInsets.navigationBars
    ) { innerPadding ->
        NavHost(
            modifier = modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
                .imePadding(),
            navController = navigationState.navHostController,
            startDestination = ExpensesFlow.ExpensesGraph.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
        ) {
            expensesGraph(navController = navigationState.navHostController)
            incomesGraph(navController = navigationState.navHostController)
            composable(Screen.Categories.route) {
                CategoriesScreen()
            }
            accountGraph(navController = navigationState.navHostController)
            composable(Screen.Options.route) {
                OptionScreen()
            }
        }
    }
}