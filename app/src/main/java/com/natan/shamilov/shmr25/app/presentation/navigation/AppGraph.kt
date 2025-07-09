package com.natan.shamilov.shmr25.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.natan.shamilov.shmr25.app.presentation.MainScreen
import com.natan.shamilov.shmr25.feature.splash.SplashFlow
import com.natan.shamilov.shmr25.feature.splash.SplashScreen

/**
 * Корневой навигационный граф приложения.
 * Ответственность: Определение основной навигационной структуры приложения,
 * включая стартовый экран (splash) и основной экран приложения.
 *
 * Навигационный граф включает:
 * - Splash экран как начальную точку входа
 * - Основной экран приложения с нижней навигацией
 *
 * Использует [NavigationState] для управления навигацией между экранами
 * и обработки специфических паттернов навигации (например, удаление splash
 * экрана из бэкстека после перехода на основной экран).
 */
@Composable
fun AppGraph() {
    val navigationState = rememberNavigationState()
    NavHost(
        navController = navigationState.navHostController,
        startDestination = SplashFlow.Splash.route
    ) {
        composable(SplashFlow.Splash.route) {
            SplashScreen(onNextScreen = {
                navigationState.splashNavigate(Screen.Main)
            })
        }

        composable(Screen.Main.route) {
            MainScreen()
        }
    }
}
