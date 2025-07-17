package com.natan.shamilov.shmr25.app.di

import android.content.Context
import com.natan.shamilov.shmr25.account.api.AccountDependencies
import com.natan.shamilov.shmr25.app.MainActivity
import com.natan.shamilov.shmr25.app.di.moduls.AccountsDependenciesModule
import com.natan.shamilov.shmr25.app.di.moduls.AppDatabaseModule
import com.natan.shamilov.shmr25.app.di.moduls.CategoriesDependenciesModule
import com.natan.shamilov.shmr25.app.di.moduls.ExpensesDependenciesModule
import com.natan.shamilov.shmr25.app.di.moduls.HistoryDependenciesModule
import com.natan.shamilov.shmr25.app.di.moduls.IncomesDependenciesModule
import com.natan.shamilov.shmr25.app.di.moduls.SplashDependenciesModule
import com.natan.shamilov.shmr25.app.di.moduls.ViewModelModule
import com.natan.shamilov.shmr25.categories.api.CategoriesDependencies
import com.natan.shamilov.shmr25.common.impl.di.BaseAccountModule
import com.natan.shamilov.shmr25.common.impl.di.BaseCategoriesModule
import com.natan.shamilov.shmr25.common.impl.di.BaseTransactionsModule
import com.natan.shamilov.shmr25.common.impl.di.CommonApiModule
import com.natan.shamilov.shmr25.common.impl.di.ViewModelFactory
import com.natan.shamilov.shmr25.common.impl.di.ViewModelFactoryModule
import com.natan.shamilov.shmr25.common.impl.di.ViewModelFactoryScope
import com.natan.shamilov.shmr25.expenses.api.ExpensesDependencies
import com.natan.shamilov.shmr25.history.api.HistoryDependencies
import com.natan.shamilov.shmr25.incomes.api.IncomesDependencies
import com.natan.shamilov.shmr25.splash.api.SplashDependencies
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@ViewModelFactoryScope
@Component(
    modules = [
        ViewModelFactoryModule::class,
        CommonApiModule::class,
        NetworkModule::class,
        com.natan.shamilov.shmr25.common.impl.di.NetworkModule::class,
        BaseAccountModule::class,
        BaseCategoriesModule::class,
        BaseTransactionsModule::class,
        SplashDependenciesModule::class,
        ExpensesDependenciesModule::class,
        IncomesDependenciesModule::class,
        HistoryDependenciesModule::class,
        AccountsDependenciesModule::class,
        CategoriesDependenciesModule::class,
        ViewModelModule::class,
        AppDatabaseModule::class
    ]
)
interface AppComponent :
    SplashDependencies,
    ExpensesDependencies,
    IncomesDependencies,
    HistoryDependencies,
    AccountDependencies,
    CategoriesDependencies {

    fun viewModelFactory(): ViewModelFactory

    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance context: Context): AppComponent
    }
}
