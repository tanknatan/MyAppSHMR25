package com.natan.shamilov.shmr25.app.presentation.navigation

import com.natan.shamilov.shmr25.R
import com.natan.shamilov.shmr25.feature.account.presentation.navigation.AccountFlow
import com.natan.shamilov.shmr25.feature.categories.presentation.navigation.CategoriesFlow
import com.natan.shamilov.shmr25.feature.expenses.presentation.navigation.ExpensesFlow
import com.natan.shamilov.shmr25.feature.incomes.presentation.navigation.IncomesFlow
import com.natan.shamilov.shmr25.feature.option.presentation.navigation.OptionsFlow
import com.natan.shamilov.shmr25.common.impl.domain.entity.Screen as commoScreen

/**
 * Sealed класс, представляющий элементы нижней навигационной панели.
 * Ответственность: Определение всех доступных пунктов навигации в нижней панели
 * приложения, включая их экраны, метки и иконки.
 *
 * @property screen Экран, связанный с пунктом навигации
 * @property label Ресурс строки для отображения названия пункта
 * @property iconId Ресурс иконки для отображения в панели навигации
 */
sealed class NavigationItem(
    val screen: commoScreen,
    val label: Int,
    val iconId: Int
) {
    /**
     * Пункт навигации для раздела расходов.
     * Ведет к графу навигации расходов.
     */
    data object Expenses : NavigationItem(
        ExpensesFlow.ExpensesGraph,
        label = R.string.expenses,
        iconId = R.drawable.expenses
    )

    /**
     * Пункт навигации для раздела доходов.
     * Ведет к графу навигации доходов.
     */
    data object Incomes : NavigationItem(
        IncomesFlow.IncomesGraph,
        label = R.string.incomes,
        iconId = R.drawable.incomes
    )

    /**
     * Пункт навигации для раздела счетов.
     * Ведет к графу навигации счетов.
     */
    data object Account : NavigationItem(
        AccountFlow.AccountGraph,
        label = R.string.account,
        iconId = R.drawable.account
    )

    /**
     * Пункт навигации для раздела категорий.
     * Ведет к графу навигации категорий.
     */
    data object Categories : NavigationItem(
        CategoriesFlow.CategoriesGraph,
        label = R.string.categories,
        iconId = R.drawable.categories
    )

    /**
     * Пункт навигации для раздела настроек.
     * Ведет к экрану настроек приложения.
     */
    data object Options : NavigationItem(
        OptionsFlow.Options,
        label = R.string.options,
        iconId = R.drawable.options
    )
}
