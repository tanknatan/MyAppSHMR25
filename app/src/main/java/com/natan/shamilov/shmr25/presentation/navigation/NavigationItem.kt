package com.natan.shamilov.shmr25.presentation.navigation

import com.natan.shamilov.shmr25.R

sealed class NavigationItem(
    val screen: Screen,
    val label: Int,
    val iconId: Int
) {
    data object Expenses: NavigationItem(
        Screen.Expenses,
        R.string.expenses,
        R.drawable.expenses
    )
    data object Incomes: NavigationItem(
        Screen.Incomes,
        R.string.incomes,
        R.drawable.incomes
    )
    data object Account: NavigationItem(
        Screen.Account,
        R.string.account,
        R.drawable.account
    )
    data object Categories: NavigationItem(
        Screen.Categories,
        R.string.categories,
        R.drawable.categories
    )
    data object Options: NavigationItem(
        Screen.Options,
        R.string.options,
        R.drawable.options
    )
}