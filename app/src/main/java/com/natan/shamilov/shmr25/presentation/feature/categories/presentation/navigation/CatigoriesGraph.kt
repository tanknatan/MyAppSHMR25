package com.natan.shamilov.shmr25.presentation.feature.categories.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.natan.shamilov.shmr25.presentation.feature.categories.presentation.screen.CategoriesScreen
import com.natan.shamilov.shmr25.presentation.navigation.NavigationState

fun NavGraphBuilder.catigoriesGraph(navController: NavigationState) {
    navigation(
        route = CategoriesFlow.CategoriesGraph.route,
        startDestination = CategoriesFlow.Categories.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(CategoriesFlow.Categories.route) {
            CategoriesScreen()
        }
    }
}
