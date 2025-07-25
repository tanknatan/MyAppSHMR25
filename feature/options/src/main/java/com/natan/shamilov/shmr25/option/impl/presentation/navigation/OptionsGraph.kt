package com.natan.shamilov.shmr25.option.impl.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.natan.shamilov.shmr25.common.impl.presentation.LocalViewModelFactory
import com.natan.shamilov.shmr25.option.impl.di.OptionsComponent
import com.natan.shamilov.shmr25.option.impl.presentation.screen.aboutAppOption.AboutAppScreen
import com.natan.shamilov.shmr25.option.impl.presentation.screen.languageOption.ChangeLanguageScreen
import com.natan.shamilov.shmr25.option.impl.presentation.screen.mainColorOption.ChangeMainColorScreen
import com.natan.shamilov.shmr25.option.impl.presentation.screen.mainOption.OptionScreen
import com.natan.shamilov.shmr25.option.impl.presentation.screen.pinCodeOption.PinCodeScreen
import com.natan.shamilov.shmr25.option.impl.presentation.screen.syncOption.SyncScreen
import com.natan.shamilov.shmr25.option.impl.presentation.screen.vibrationOption.VibrationScreen
import com.natan.shamilov.shmr25.options.R

fun NavGraphBuilder.optionsGraph(
    navHostController: NavHostController,
    optionsComponent: OptionsComponent,
) {
    navigation(
        route = OptionsFlow.OptionsGraph.route,
        startDestination = OptionsFlow.Options.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(OptionsFlow.Options.route) {
            CompositionLocalProvider(
                LocalViewModelFactory provides optionsComponent.viewModelFactory()
            ) {
                OptionScreen(
                    onChoseOption = { id ->
                        when (id) {
                            R.string.main_color -> navHostController.navigate(OptionsFlow.MainColor.route)
                            R.string.vibration -> navHostController.navigate(OptionsFlow.Vibration.route)
                            R.string.password -> navHostController.navigate(OptionsFlow.PinCode.route)
                            R.string.sync -> navHostController.navigate(OptionsFlow.Sync.route)
                            R.string.language -> navHostController.navigate(OptionsFlow.Language.route)
                            R.string.about_app -> navHostController.navigate(OptionsFlow.AboutApp.route)
                        }
                    }
                )
            }
        }
        composable(OptionsFlow.MainColor.route) {
            CompositionLocalProvider(
                LocalViewModelFactory provides optionsComponent.viewModelFactory()
            ) {
                ChangeMainColorScreen(onBackClick = { navHostController.popBackStack() })
            }
        }
        composable(OptionsFlow.Sound.route) {
        }
        composable(OptionsFlow.Vibration.route) {
            CompositionLocalProvider(
                LocalViewModelFactory provides optionsComponent.viewModelFactory()
            ) {
                VibrationScreen(onBackClick = { navHostController.popBackStack() })
            }
        }
        composable(OptionsFlow.PinCode.route) {
            CompositionLocalProvider(
                LocalViewModelFactory provides optionsComponent.viewModelFactory()
            ) {
                PinCodeScreen(onBackClick = { navHostController.popBackStack() })
            }
        }
        composable(OptionsFlow.Sync.route) {
            CompositionLocalProvider(
                LocalViewModelFactory provides optionsComponent.viewModelFactory()
            ) {
                SyncScreen(onBackClick = { navHostController.popBackStack() })
            }
        }
        composable(OptionsFlow.Language.route) {
            CompositionLocalProvider(
                LocalViewModelFactory provides optionsComponent.viewModelFactory()
            ) {
                ChangeLanguageScreen(onBackClick = { navHostController.popBackStack() })
            }
        }
        composable(OptionsFlow.AboutApp.route) {
            CompositionLocalProvider(
                LocalViewModelFactory provides optionsComponent.viewModelFactory()
            ) {
                AboutAppScreen(onBackClick = { navHostController.popBackStack() })
            }
        }
    }
}
