package com.natan.shamilov.shmr25.expenses.impl.di

import androidx.lifecycle.ViewModel
import com.natan.shamilov.shmr25.common.impl.di.ViewModelKey
import com.natan.shamilov.shmr25.expenses.impl.presentation.screen.addExpenses.AddExpensesViewModel
import com.natan.shamilov.shmr25.expenses.impl.presentation.screen.editExpenses.EditExpensesViewModel
import com.natan.shamilov.shmr25.expenses.impl.presentation.screen.todayExpenses.ExpensesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ExpensesViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ExpensesViewModel::class)
    fun bindExpensesViewModel(expensesViewModel: ExpensesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddExpensesViewModel::class)
    fun bindAddExpensesViewModel(addExpensesViewModel: AddExpensesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditExpensesViewModel::class)
    fun bindEditExpensesViewModel(editExpensesViewModel: EditExpensesViewModel): ViewModel
}
