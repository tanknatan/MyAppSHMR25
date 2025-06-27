package com.natan.shamilov.shmr25.feature.categories.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.natan.shamilov.shmr25.app.navigation.NavigationState
import com.natan.shamilov.shmr25.feature.categories.presentation.screen.CategoriesScreen

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
