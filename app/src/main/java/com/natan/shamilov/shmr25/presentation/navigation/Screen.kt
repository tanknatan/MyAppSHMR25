package com.natan.shamilov.shmr25.presentation.navigation

import com.natan.shamilov.shmr25.R
import com.natan.shamilov.shmr25.commo.Screen as commoScreen

sealed class Screen(
    override val route: String,
    val title: Int? = null,
    val endIcone: Int? = null,
    val startIcone: Int? = null,
) :
    commoScreen(route) {

    object Main : Screen(MAIN_ROUTE)

    object Splash : Screen(SPLASH_ROUTE)

    object Expenses : Screen(route = EXPENSES_ROUTE, title = R.string.expenses_today, R.drawable.ic_history, null)
    object ExpensesHistory :
        Screen(
            route = EXPENSES_HISTORY_ROUTE,
            title = R.string.my_history,
            R.drawable.ic_analytics,
            R.drawable.ic_back
        )

    object Incomes : Screen(route = INCOMES_ROUTE, title = R.string.incomes_today, R.drawable.ic_history, null)
    object IncomesHistory :
        Screen(
            route = INCOMES_HISTORY_ROUTE,
            title = R.string.my_history,
            R.drawable.ic_analytics,
            R.drawable.ic_back
        )
    object Account : Screen(route = ACCOUNT_ROUTE, title = R.string.my_account, R.drawable.ic_edit, null)
    object AddAccount :
        Screen(
            route = ACCOUNT_ROUTE,
            title = R.string.add_account,
            R.drawable.ic_close,
            R.drawable.ic_accept
        )
    object Categories : Screen(route = CATEGORIES_ROUTE, title = R.string.my_categories, null, null)
    object Options : Screen(route = OPTIONS_ROUTE, title = R.string.options, null, null)


    companion object {
        const val MAIN_ROUTE = "main"

        const val SPLASH_ROUTE = "splash"

        const val EXPENSES_ROUTE = "expenses"
        const val EXPENSES_HISTORY_ROUTE = "expenses_history"
        const val INCOMES_ROUTE = "incomes"
        const val INCOMES_HISTORY_ROUTE = "incomes_history"
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

        fun Screen.areSplashScreen(): Boolean {
            return this == Splash
        }
    }
}