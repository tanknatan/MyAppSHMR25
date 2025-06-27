package com.natan.shamilov.shmr25.app.navigation

import com.natan.shamilov.shmr25.R
import com.natan.shamilov.shmr25.common.Screen as commoScreen
import com.natan.shamilov.shmr25.feature.account.presentation.navigation.AccountFlow
import com.natan.shamilov.shmr25.feature.categories.presentation.navigation.CategoriesFlow
import com.natan.shamilov.shmr25.feature.expenses.presentation.navigation.ExpensesFlow
import com.natan.shamilov.shmr25.feature.incomes.presentation.navigation.IncomesFlow
import com.natan.shamilov.shmr25.feature.option.presentation.navigation.OptionsFlow

sealed class NavigationItem(
    val screen: commoScreen,
    val label: Int,
    val iconId: Int
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
        CategoriesFlow.CategoriesGraph,
        label = R.string.categories,
        iconId = R.drawable.categories
    )

    data object Options : NavigationItem(
        OptionsFlow.Options,
        label = R.string.options,
        iconId = R.drawable.options
    )
}
