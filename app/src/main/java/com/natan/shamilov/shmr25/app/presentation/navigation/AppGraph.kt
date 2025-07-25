package com.natan.shamilov.shmr25.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.natan.shamilov.shmr25.app.appComponent
import com.natan.shamilov.shmr25.app.presentation.MainScreen
import com.natan.shamilov.shmr25.common.impl.presentation.LocalViewModelFactory
import com.natan.shamilov.shmr25.login.impl.di.DaggerLoginComponent
import com.natan.shamilov.shmr25.login.impl.presentation.navigation.LoginFlow
import com.natan.shamilov.shmr25.login.impl.presentation.screen.PinCodeLoginScreen
import com.natan.shamilov.shmr25.splash.impl.SplashFlow
import com.natan.shamilov.shmr25.splash.impl.SplashScreen
import com.natan.shamilov.shmr25.splash.impl.di.DaggerSplashComponent

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
    val context = LocalContext.current
    val appComponent = context.appComponent
    var syncInfo by remember { mutableStateOf(Pair<Long?, String?>(null, null)) }
    LaunchedEffect(Unit) {
        syncInfo = appComponent.syncPreferencesProvider().getLastSyncInfo()
    }
    NavHost(
        navController = navigationState.navHostController,
        startDestination = SplashFlow.Splash.route
    ) {
        composable(SplashFlow.Splash.route) {
            val splashComponent = DaggerSplashComponent.factory().create(appComponent)
            CompositionLocalProvider(
                LocalViewModelFactory provides splashComponent.viewModelFactory()
            ) {
                SplashScreen(
                    onMainScreen = {
                        navigationState.splashNavigate(Screen.Main)
                    },
                    onLoginScreen = {
                        navigationState.splashNavigate(LoginFlow.Login)
                    }
                )
            }
        }

        composable(LoginFlow.Login.route) {
            val loginComponent = DaggerLoginComponent.factory().create(appComponent)
            CompositionLocalProvider(
                LocalViewModelFactory provides loginComponent.viewModelFactory()
            ) {
                PinCodeLoginScreen(onContinue = {
                    navigationState.splashNavigate(Screen.Main)
                })
            }
        }

        composable(Screen.Main.route) {
            MainScreen(syncInfo = syncInfo)
        }
    }
}
