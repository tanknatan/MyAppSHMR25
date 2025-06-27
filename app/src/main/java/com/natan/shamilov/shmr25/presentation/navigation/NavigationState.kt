package com.natan.shamilov.shmr25.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.natan.shamilov.shmr25.common.Screen

class NavigationState(
    val navHostController: NavHostController
) {
    fun bottomNavigate(screen: Screen) {
        navHostController.navigate(screen.route) {
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

    fun navigateSingleTopTo(screen: Screen) {
        val currentRoute = navHostController.currentBackStackEntry?.destination?.route ?: return
        navHostController.navigate(screen.route) {
            popUpTo(currentRoute) {
                saveState = false
            }

            launchSingleTop = false
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
