package com.natan.shamilov.shmr25.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.natan.shamilov.shmr25.presentation.MainScreen
import com.natan.shamilov.shmr25.presentation.feature.splash.SplashScreen

@Composable
fun AppGraph() {
    val navigationState = rememberNavigationState()
    NavHost(
        navController = navigationState.navHostController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(onNextScreen = {
                navigationState.splashNavigate(Screen.Main)
            })
        }

        composable(Screen.Main.route) {
            MainScreen()
        }
    }
}
