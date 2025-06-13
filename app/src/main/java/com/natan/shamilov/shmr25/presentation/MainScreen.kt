package com.natan.shamilov.shmr25.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.natan.shamilov.shmr25.R
import com.natan.shamilov.shmr25.presentation.navigation.MainNavigation
import com.natan.shamilov.shmr25.presentation.navigation.NavigationItem
import com.natan.shamilov.shmr25.presentation.navigation.Screen
import com.natan.shamilov.shmr25.presentation.navigation.Screen.Companion.areScreenWithFab
import com.natan.shamilov.shmr25.presentation.navigation.rememberNavigationState
import com.natan.shamilov.shmr25.presentation.screens.account.AccountScreen
import com.natan.shamilov.shmr25.presentation.screens.categories.CategoriesScreen
import com.natan.shamilov.shmr25.presentation.screens.expenses.ExpensesScreen
import com.natan.shamilov.shmr25.presentation.screens.incomes.IncomesScreen
import com.natan.shamilov.shmr25.presentation.screens.option.OptionsScreen
import com.natan.shamilov.shmr25.ui.CustomTopAppBar
import com.natan.shamilov.shmr25.ui.theme.BottomBarBackground
import com.natan.shamilov.shmr25.ui.theme.rodotoFont

@Composable
fun MainScreen() {
    val navigationState = rememberNavigationState()
    val navBackStackEntry = navigationState.navHostController.currentBackStackEntryAsState()
    val currentScreen = Screen.fromRoute(navBackStackEntry.value?.destination?.route)

    val navigationList = listOf(
        NavigationItem.Expenses,
        NavigationItem.Incomes,
        NavigationItem.Account,
        NavigationItem.Categories,
        NavigationItem.Options
    )
    Scaffold(
        topBar = {
            if (currentScreen != null) {
                CustomTopAppBar(
                    currentScreen.startIcone,
                    currentScreen.title,
                    currentScreen.endIcone,
                    onBackOrCanselClick = {},
                    onNavigateClick = { },
                )
            }
        },
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
                            if (!selected) navigationState.bottomNavigate(item.screen.route)
                        },
                        icon = {
                            Image(
                                painter = painterResource(item.iconId),
                                contentDescription = null,
                                colorFilter = if (selected) {
                                    ColorFilter.tint(
                                        MaterialTheme.colorScheme.primary
                                    )
                                } else null
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(item.label),
                                fontSize = 12.sp,
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
        floatingActionButton = {
            if (currentScreen?.areScreenWithFab() == true) {
                FloatingActionButton(
                    onClick = {},
                    shape = CircleShape,
                    containerColor = MaterialTheme.colorScheme.primary,
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 0.dp,
                        focusedElevation = 0.dp,
                        hoveredElevation = 0.dp,
                    )
                ) {
                    Image(painter = painterResource(R.drawable.ic_plus), contentDescription = null)
                }
            }
        }
    ) { innerPadding ->
        MainNavigation(
            navController = navigationState.navHostController,
            expensesScreenContent = {ExpensesScreen(paddingValues = innerPadding)},
            incomesScreenContent = {IncomesScreen(paddingValues = innerPadding)},
            accountScreenContent = { AccountScreen(paddingValues = innerPadding) },
            categoriesScreenContent = { CategoriesScreen(paddingValues = innerPadding) },
            optionsScreenContent = { OptionsScreen(innerPadding) },
        )
    }
}