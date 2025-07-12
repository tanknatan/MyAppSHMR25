package com.natan.shamilov.shmr25.categories.impl.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.natan.shamilov.shmr25.categories.impl.di.CategoriesComponent
import com.natan.shamilov.shmr25.common.impl.presentation.LocalViewModelFactory
import com.natan.shamilov.shmr25.feature.categories.presentation.navigation.CategoriesFlow
import com.natan.shamilov.shmr25.feature.categories.presentation.screen.CategoriesScreen

fun NavGraphBuilder.catigoriesGraph(navHostController: NavHostController, categoriesComponent: CategoriesComponent) {
    navigation(
        route = CategoriesFlow.CategoriesGraph.route,
        startDestination = CategoriesFlow.Categories.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(CategoriesFlow.Categories.route) {
            CompositionLocalProvider(
                LocalViewModelFactory provides categoriesComponent.viewModelFactory()
            ) {
                CategoriesScreen()
            }
        }
    }
}
