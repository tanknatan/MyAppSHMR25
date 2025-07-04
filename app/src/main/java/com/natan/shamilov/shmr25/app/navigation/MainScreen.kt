package com.natan.shamilov.shmr25.app.navigation

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
import androidx.navigation.compose.currentBackStackEntryAsState
import com.natan.shamilov.shmr25.common.ui.MyNavigationBar
import com.natan.shamilov.shmr25.feature.account.presentation.navigation.accountGraph
import com.natan.shamilov.shmr25.feature.categories.presentation.navigation.catigoriesGraph
import com.natan.shamilov.shmr25.feature.expenses.presentation.navigation.ExpensesFlow
import com.natan.shamilov.shmr25.feature.expenses.presentation.navigation.expensesGraph
import com.natan.shamilov.shmr25.feature.history.presentation.navigation.historyGraph
import com.natan.shamilov.shmr25.feature.incomes.presentation.navigation.incomesGraph
import com.natan.shamilov.shmr25.feature.option.presentation.navigation.optionsGraph

/**
 * Основной экран приложения.
 * Ответственность: Организация основного UI приложения, включая нижнюю навигационную
 * панель и контейнер для отображения различных разделов приложения.
 *
 * Экран содержит:
 * - Нижнюю навигационную панель с основными разделами (расходы, доходы, счета и т.д.)
 * - Навигационный хост для отображения содержимого выбранного раздела
 * - Настройку навигации между различными графами (expenses, incomes, account и т.д.)
 *
 * @param modifier Модификатор для настройки внешнего вида и поведения экрана
 */
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
