package com.natan.shamilov.shmr25.presentation.navigation

import com.natan.shamilov.shmr25.R
import com.natan.shamilov.shmr25.presentation.feature.account.presentation.navigation.AccountFlow
import com.natan.shamilov.shmr25.presentation.feature.expenses.presentation.navigation.ExpensesFlow
import com.natan.shamilov.shmr25.presentation.feature.incomes.presentation.navigation.IncomesFlow
import com.natan.shamilov.shmr25.commo.Screen as commoScreen

sealed class NavigationItem(
    val screen: commoScreen,
    val label: Int,
    val iconId: Int,
) {
    data object Expenses : NavigationItem(
        ExpensesFlow.ExpensesGraph,
        label = R.string.expenses,
        iconId = R.drawable.expenses
    )

    data object Incomes : NavigationItem(
        IncomesFlow.IncomesGraph,
        label = R.string.incomes,
        iconId = R.drawable.incomes
    )

    data object Account : NavigationItem(
        AccountFlow.AccountGraph,
        label = R.string.account,
        iconId = R.drawable.account
    )

    data object Categories : NavigationItem(
        Screen.Categories,
        label = R.string.categories,
        iconId = R.drawable.categories
    )

    data object Options : NavigationItem(
        Screen.Options,
        label = R.string.options,
        iconId = R.drawable.options
    )
}