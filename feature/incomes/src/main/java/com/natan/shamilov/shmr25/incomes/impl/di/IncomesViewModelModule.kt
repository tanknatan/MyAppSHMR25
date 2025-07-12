package com.natan.shamilov.shmr25.incomes.impl.di

import androidx.lifecycle.ViewModel
import com.natan.shamilov.shmr25.common.impl.di.ViewModelKey
import com.natan.shamilov.shmr25.expenses.impl.presentation.screen.addExpenses.AddIncomesViewModel
import com.natan.shamilov.shmr25.expenses.impl.presentation.screen.editIncomes.EditIncomesViewModel
import com.natan.shamilov.shmr25.incomes.impl.presentation.screen.todayIncomes.IncomesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface IncomesViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(IncomesViewModel::class)
    fun bindIncomesViewModel(incomesViewModel: IncomesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddIncomesViewModel::class)
    fun bindAddIncomesViewModel(addIncomeViewModel: AddIncomesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditIncomesViewModel::class)
    fun bindEditIncomesViewModel(editIncomeViewModel: EditIncomesViewModel): ViewModel
}