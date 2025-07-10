package com.natan.shamilov.shmr25.app.di

import android.app.Application
import com.natan.shamilov.shmr25.app.MainActivity
import com.natan.shamilov.shmr25.common.network.NetworkStateReceiver
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        com.natan.shamilov.shmr25.common.di.AccountModule::class,
        com.natan.shamilov.shmr25.common.di.AccountApiModule::class,
        com.natan.shamilov.shmr25.feature.account.di.AccountModule::class,
        com.natan.shamilov.shmr25.feature.incomes.di.IncomesModule::class,
        com.natan.shamilov.shmr25.feature.expenses.di.ExpensesModule::class,
        com.natan.shamilov.shmr25.feature.categories.di.CategoriesModule::class,
        com.natan.shamilov.shmr25.feature.history.di.HistoryModule::class
    ]
)
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
    fun inject(activity: MainActivity)
    // Методы-провайдеры для ViewModelFactory
    fun getCategoriesListUseCase(): com.natan.shamilov.shmr25.feature.categories.domain.usecase.GetCategoriesListUseCase
    fun loadCategoriesListUseCase(): com.natan.shamilov.shmr25.feature.categories.domain.usecase.LoadCategoriesListUseCase
    fun networkStateReceiver(): NetworkStateReceiver
    fun getAccountObserverUseCase(): com.natan.shamilov.shmr25.feature.account.domain.usecase.GetAccountObserverUseCase
    fun getAccountUseCase(): com.natan.shamilov.shmr25.feature.account.domain.usecase.GetAccountUseCase
    fun getSelectedAccountUseCase(): com.natan.shamilov.shmr25.feature.account.domain.usecase.GetSelectedAccountUseCase
    fun setSelectedAccountUseCase(): com.natan.shamilov.shmr25.feature.account.domain.usecase.SetSelectedAccountUseCase
    fun createAccountUseCase(): com.natan.shamilov.shmr25.feature.account.domain.usecase.CreateAccountUseCase
    fun accountRepository(): com.natan.shamilov.shmr25.common.domain.repository.AccountRepository
    fun editAccountUseCase(): com.natan.shamilov.shmr25.feature.account.domain.usecase.EditAccountUseCase
    fun loadIncomesByPeriodUseCase(): com.natan.shamilov.shmr25.feature.incomes.domain.usecase.LoadIncomesByPeriodUseCase
    fun getIncomesListUseCase(): com.natan.shamilov.shmr25.feature.incomes.domain.usecase.GetIncomesListUseCase
    fun loadExpensesByPeriodUseCase(): com.natan.shamilov.shmr25.feature.expenses.domain.usecase.LoadExpensesByPeriodUseCase
    fun getExpensesListUseCase(): com.natan.shamilov.shmr25.feature.expenses.domain.usecase.GetExpensesListUseCase
    fun getHistoryByPeriodUseCase(): com.natan.shamilov.shmr25.feature.history.domain.usecase.GetHistoryByPeriodUseCase
    fun accountStartupLoader(): com.natan.shamilov.shmr25.feature.splash.AccountStartupLoader
    // Позже добавим inject для ViewModel и других компонентов
} 