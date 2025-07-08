package com.natan.shamilov.shmr25.app.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.natan.shamilov.shmr25.app.App
import com.natan.shamilov.shmr25.feature.account.presentation.screen.accounts.AccountViewModel
import com.natan.shamilov.shmr25.feature.account.presentation.screen.addAccount.AddAccountViewModel
import com.natan.shamilov.shmr25.feature.account.presentation.screen.editAccount.EditAccountViewModel
import com.natan.shamilov.shmr25.feature.categories.presentation.screen.CategoriesViewModel
import com.natan.shamilov.shmr25.feature.expenses.presentation.screen.ExpensesViewModel
import com.natan.shamilov.shmr25.feature.history.presentation.screen.HistoryViewModel
import com.natan.shamilov.shmr25.feature.incomes.presentation.screen.IncomesViewModel
import com.natan.shamilov.shmr25.feature.splash.SplashViewModel

class DaggerViewModelFactory(private val app: App) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val component = app.appComponent
        @Suppress("UNCHECKED_CAST")
        return when (modelClass) {
            CategoriesViewModel::class.java -> CategoriesViewModel(
                component.getCategoriesListUseCase(),
                component.loadCategoriesListUseCase(),
                component.networkStateReceiver()
            ) as T
            AccountViewModel::class.java -> AccountViewModel(
                component.networkStateReceiver(),
                component.getAccountObserverUseCase(),
                component.getAccountUseCase(),
                component.getSelectedAccountUseCase(),
                component.setSelectedAccountUseCase()
            ) as T
            AddAccountViewModel::class.java -> AddAccountViewModel(
                component.createAccountUseCase(),
                component.networkStateReceiver()
            ) as T
            EditAccountViewModel::class.java -> EditAccountViewModel(
                component.editAccountUseCase(),
                component.getAccountUseCase(),
                component.networkStateReceiver(),
            ) as T
            IncomesViewModel::class.java -> IncomesViewModel(
                component.getIncomesListUseCase(),
                component.loadIncomesByPeriodUseCase(),
                component.networkStateReceiver()
            ) as T
            ExpensesViewModel::class.java -> ExpensesViewModel(
                component.getExpensesListUseCase(),
                component.loadExpensesByPeriodUseCase(),
                component.networkStateReceiver()
            ) as T
            HistoryViewModel::class.java -> HistoryViewModel(
                component.getHistoryByPeriodUseCase(),
                component.networkStateReceiver()
            ) as T
            SplashViewModel::class.java -> SplashViewModel(
                component.accountStartupLoader()
            ) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}

object ApplicationHolder {
    lateinit var application: App
}
