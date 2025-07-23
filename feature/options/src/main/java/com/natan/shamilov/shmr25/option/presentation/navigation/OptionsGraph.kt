package com.natan.shamilov.shmr25.option.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.natan.shamilov.shmr25.option.presentation.screen.aboutAppOption.AboutAppScreen
import com.natan.shamilov.shmr25.option.presentation.screen.languageOption.ChangeLanguageScreen
import com.natan.shamilov.shmr25.option.presentation.screen.mainColorOption.ChangeMainColorScreen
import com.natan.shamilov.shmr25.option.presentation.screen.mainOption.OptionScreen
import com.natan.shamilov.shmr25.option.presentation.screen.pinCodeOption.PinCodeScreen
import com.natan.shamilov.shmr25.option.presentation.screen.syncOption.SyncScreen
import com.natan.shamilov.shmr25.option.presentation.screen.vibrationOption.VibrationScreen
import com.natan.shamilov.shmr25.options.R

fun NavGraphBuilder.optionsGraph(navHostController: NavHostController) {
    navigation(
        route = OptionsFlow.OptionsGraph.route,
        startDestination = OptionsFlow.Options.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(OptionsFlow.Options.route) {
            OptionScreen(
                onChoseOption = { id ->
                    when (id) {
                        R.string.main_color -> navHostController.navigate(OptionsFlow.MainColor.route)
                        R.string.sound -> navHostController.navigate(OptionsFlow.Sound.route)
                        R.string.vibration -> navHostController.navigate(OptionsFlow.Vibration.route)
                        R.string.password -> navHostController.navigate(OptionsFlow.PinCode.route)
                        R.string.sync -> navHostController.navigate(OptionsFlow.Sync.route)
                        R.string.language -> navHostController.navigate(OptionsFlow.Language.route)
                        R.string.about_app -> navHostController.navigate(OptionsFlow.AboutApp.route)
                    }
                }
            )
        }
        composable(OptionsFlow.MainColor.route) {
            ChangeMainColorScreen(onBackClick = { navHostController.popBackStack() })
        }
        composable(OptionsFlow.Sound.route) {
        }
        composable(OptionsFlow.Vibration.route) {
            VibrationScreen(onBackClick = { navHostController.popBackStack() })
        }
        composable(OptionsFlow.PinCode.route) {
            PinCodeScreen(onBackClick = { navHostController.popBackStack() })
        }
        composable(OptionsFlow.Sync.route) {
            SyncScreen(onBackClick = { navHostController.popBackStack() })
        }
        composable(OptionsFlow.Language.route) {
            ChangeLanguageScreen(onBackClick = { navHostController.popBackStack() })
        }
        composable(OptionsFlow.AboutApp.route) {
            AboutAppScreen(onBackClick = { navHostController.popBackStack() })
        }
    }
}
