package com.natan.shamilov.shmr25.feature.option.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.natan.shamilov.shmr25.app.navigation.NavigationState
import com.natan.shamilov.shmr25.feature.option.presentation.screen.OptionScreen

fun NavGraphBuilder.optionsGraph(navController: NavigationState) {
    navigation(
        route = OptionsFlow.OptionsGraph.route,
        startDestination = OptionsFlow.Options.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(OptionsFlow.Options.route) {
            OptionScreen()
        }
    }
}
