package com.natan.shamilov.shmr25.app.presentation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.natan.shamilov.shmr25.account.impl.di.DaggerAccountsComponent
import com.natan.shamilov.shmr25.app.appComponent
import com.natan.shamilov.shmr25.app.presentation.components.MyNavigationBar
import com.natan.shamilov.shmr25.app.presentation.components.SyncSnackbar
import com.natan.shamilov.shmr25.app.presentation.navigation.NavigationItem
import com.natan.shamilov.shmr25.app.presentation.navigation.rememberNavigationState
import com.natan.shamilov.shmr25.categories.impl.di.DaggerCategoriesComponent
import com.natan.shamilov.shmr25.categories.impl.presentation.navigation.catigoriesGraph
import com.natan.shamilov.shmr25.common.impl.presentation.LocalViewModelFactory
import com.natan.shamilov.shmr25.expenses.impl.di.DaggerExpensesComponent
import com.natan.shamilov.shmr25.expenses.impl.presentation.navigation.ExpensesFlow
import com.natan.shamilov.shmr25.feature.account.presentation.navigation.accountGraph
import com.natan.shamilov.shmr25.feature.expenses.presentation.navigation.expensesGraph
import com.natan.shamilov.shmr25.history.impl.di.DaggerHistoryComponent
import com.natan.shamilov.shmr25.history.impl.presentation.navigation.historyGraph
import com.natan.shamilov.shmr25.incomes.impl.di.DaggerIncomesComponent
import com.natan.shamilov.shmr25.incomes.impl.presentation.navigation.incomesGraph
import com.natan.shamilov.shmr25.option.impl.di.DaggerOptionsComponent
import com.natan.shamilov.shmr25.option.impl.presentation.navigation.optionsGraph
import kotlinx.coroutines.launch

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
fun MainScreen(
    modifier: Modifier = Modifier, syncInfo: Pair<Long?, String?>,
    viewModel: MainViewModel = viewModel(factory = LocalViewModelFactory.current),
) {
    val navigationState = rememberNavigationState()
    val navBackStackEntry = navigationState.navHostController.currentBackStackEntryAsState()
    val context = LocalContext.current
    val appComponent = context.appComponent
    val navigationList = listOf(
        NavigationItem.Expenses,
        NavigationItem.Incomes,
        NavigationItem.Account,
        NavigationItem.Categories,
        NavigationItem.Options
    )
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            snackbarHostState.showSnackbar(
                message = "Добро пожаловать!",
            )
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            MyNavigationBar(
                navigationList = navigationList,
                navigationState = navigationState,
                navBackStackEntry = navBackStackEntry,
                onHaptiv = { viewModel.vibrate() }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                SyncSnackbar(
                    lastSyncTime = syncInfo.first ?: 0L,
                    lastSyncStatus = syncInfo.second.orEmpty(),
                    onDismiss = { snackbarHostState.currentSnackbarData?.dismiss() }
                )
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
            exitTransition = { ExitTransition.None }
        ) {
            expensesGraph(
                navHostController = navigationState.navHostController,
                DaggerExpensesComponent.factory().create(appComponent)
            )
            incomesGraph(
                navHostController = navigationState.navHostController,
                DaggerIncomesComponent.factory().create(appComponent)
            )
            accountGraph(
                navHostController = navigationState.navHostController,
                DaggerAccountsComponent.factory().create(appComponent)
            )
            catigoriesGraph(
                navHostController = navigationState.navHostController,
                DaggerCategoriesComponent.factory().create(appComponent)
            )
            optionsGraph(
                navHostController = navigationState.navHostController,
                DaggerOptionsComponent.factory().create(appComponent)
            )
            historyGraph(
                navHostController = navigationState.navHostController,
                DaggerHistoryComponent.factory().create(appComponent)
            )
        }
    }
}
