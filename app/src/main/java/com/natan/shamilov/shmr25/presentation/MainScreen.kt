package com.natan.shamilov.shmr25.presentation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.natan.shamilov.shmr25.presentation.feature.account.presentation.navigation.accountGraph
import com.natan.shamilov.shmr25.presentation.feature.categories.presentation.navigation.catigoriesGraph
import com.natan.shamilov.shmr25.presentation.feature.expenses.presentation.navigation.ExpensesFlow
import com.natan.shamilov.shmr25.presentation.feature.expenses.presentation.navigation.expensesGraph
import com.natan.shamilov.shmr25.presentation.feature.history.presentation.navigation.historyGraph
import com.natan.shamilov.shmr25.presentation.feature.incomes.presentation.navigation.incomesGraph
import com.natan.shamilov.shmr25.presentation.feature.option.presentation.navigation.optionsGraph
import com.natan.shamilov.shmr25.presentation.navigation.NavigationItem
import com.natan.shamilov.shmr25.presentation.navigation.rememberNavigationState
import com.natan.shamilov.shmr25.ui.MyNavigationBar

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
            MyNavigationBar(
                navigationList = navigationList,
                navigationState = navigationState,
                navBackStackEntry = navBackStackEntry
            )
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
            exitTransition = { ExitTransition.None }
        ) {
            expensesGraph(navController = navigationState)
            incomesGraph(navController = navigationState)
            accountGraph(navController = navigationState)
            catigoriesGraph(navController = navigationState)
            optionsGraph(navController = navigationState)
            historyGraph(navController = navigationState)
        }
    }
}
