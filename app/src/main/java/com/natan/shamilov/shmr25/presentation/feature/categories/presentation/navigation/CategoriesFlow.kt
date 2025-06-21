package com.natan.shamilov.shmr25.presentation.feature.categories.presentation.navigation

import com.natan.shamilov.shmr25.commo.Screen



sealed class CategoriesFlow(val route: String) {

    data object Categories : CategoriesFlow(CATEGORIES_ROUTE)


    companion object {
        const val CATEGORIES_ROUTE = "categories_route"

    }
}