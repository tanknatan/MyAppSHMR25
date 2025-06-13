package com.natan.shamilov.shmr25.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


class NavigationState(
    val navHostController: NavHostController
) {
    fun bottomNavigate(route: String) {
        navHostController.navigate(route) {
            popUpTo(id = navHostController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun splashNavigate(screen: Screen) {
        val currentRoute = navHostController.currentBackStackEntry?.destination?.route ?: return
        navHostController.navigate(screen.route) {
            popUpTo(currentRoute) {
                inclusive = true

            }
        }
    }

    fun navigateSingleTopTo(route: String) {
        navHostController.navigate(route) {
            launchSingleTop = true
        }
    }
}

@Composable
fun rememberNavigationState(
    navHostController: NavHostController = rememberNavController()
): NavigationState {
    return remember {
        NavigationState(navHostController)
    }
}
