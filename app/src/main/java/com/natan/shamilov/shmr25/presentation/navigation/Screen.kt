package com.natan.shamilov.shmr25.presentation.navigation

import com.natan.shamilov.shmr25.R

sealed class Screen(val route: String, val title: Int? = null, val endIcone: Int? = null, val startIcone: Int? = null) {
    
    object Main : Screen(MAIN_ROUTE)

    object Splash : Screen(SPLASH_ROUTE)

    object Expenses : Screen(EXPENSES_ROUTE, R.string.expenses_today, R.drawable.ic_history, null)
    object Incomes : Screen(INCOMES_ROUTE, R.string.incomes_today, R.drawable.ic_history, null)
    object Account : Screen(ACCOUNT_ROUTE, R.string.my_account, R.drawable.ic_edit, null)
    object Categories : Screen(CATEGORIES_ROUTE, R.string.my_categories, null, null)
    object Options : Screen(OPTIONS_ROUTE, R.string.options, null, null)


    companion object {
        const val MAIN_ROUTE = "main"

        const val SPLASH_ROUTE = "splash"

        const val EXPENSES_ROUTE = "expenses"
        const val INCOMES_ROUTE = "incomes"
        const val ACCOUNT_ROUTE = "account"
        const val CATEGORIES_ROUTE = "categories"
        const val OPTIONS_ROUTE = "options"

        private val screens = listOf(Expenses, Incomes, Account, Categories, Options)

        fun fromRoute(route: String?): Screen? {
            val baseRoute = route?.substringBefore("/")
            return screens.find { it.route == baseRoute }
        }

        fun Screen.areScreenWithFab(): Boolean {
            return this in listOf(
                Expenses,
                Incomes,
                Account,
            )
        }
    }
}