package com.natan.shamilov.shmr25.presentation.navigation

import com.natan.shamilov.shmr25.R
import com.natan.shamilov.shmr25.common.Screen as commoScreen

sealed class Screen(
    override val route: String,
    val title: Int? = null,
    val endIcone: Int? = null,
    val startIcone: Int? = null
) :
    commoScreen {

    data object Main : Screen(MAIN_ROUTE)

    data object Splash : Screen(SPLASH_ROUTE)

    data object Expenses : Screen(
        route = EXPENSES_ROUTE,
        title = R.string.expenses_today,
        R.drawable.ic_history,
        null
    )
    data object ExpensesHistory :
        Screen(
            route = EXPENSES_HISTORY_ROUTE,
            title = R.string.my_history,
            R.drawable.ic_analytics,
            R.drawable.ic_back
        )

    data object Incomes : Screen(
        route = INCOMES_ROUTE,
        title = R.string.incomes_today,
        R.drawable.ic_history,
        null
    )
    data object IncomesHistory :
        Screen(
            route = INCOMES_HISTORY_ROUTE,
            title = R.string.my_history,
            R.drawable.ic_analytics,
            R.drawable.ic_back
        )

    data object Account : Screen(
        route = ACCOUNT_ROUTE,
        title = R.string.my_account,
        R.drawable.ic_edit,
        null
    )
    data object AddAccount :
        Screen(
            route = ADD_ACCOUNT_ROUTE,
            title = R.string.add_account,
            null,
            R.drawable.ic_close
        )

    data object Categories : Screen(
        route = CATEGORIES_ROUTE,
        title = R.string.my_categories,
        null,
        null
    )
    data object Options : Screen(route = OPTIONS_ROUTE, title = R.string.options, null, null)
    companion object {
        const val MAIN_ROUTE = "main"

        const val SPLASH_ROUTE = "splash"

        const val EXPENSES_ROUTE = "expenses"
        const val EXPENSES_HISTORY_ROUTE = "expenses_history"
        const val INCOMES_ROUTE = "incomes"
        const val INCOMES_HISTORY_ROUTE = "incomes_history"
        const val ACCOUNT_ROUTE = "account"
        const val ADD_ACCOUNT_ROUTE = "add_account"
        const val CATEGORIES_ROUTE = "categories"
        const val OPTIONS_ROUTE = "options"

        private val screens = listOf(Expenses, Incomes, Account, Categories, Options)

        fun fromRoute(route: String?): Screen? {
            val baseRoute = route?.substringBefore("/")
            return screens.find { it.route == baseRoute }
        }
    }
}
