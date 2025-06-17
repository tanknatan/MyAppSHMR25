package com.natan.shamilov.shmr25.presentation.screens.expenses

import androidx.lifecycle.ViewModel
import com.natan.shamilov.shmr25.domain.entity.Expense
import com.natan.shamilov.shmr25.domain.usecase.GetExpensesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class ExpensesViewModel @Inject constructor(
    private val getExpensesListUseCase: GetExpensesListUseCase
): ViewModel() {
    private val _myExpenses = MutableStateFlow<List<Expense>>(emptyList())
    val myExpenses: StateFlow<List<Expense>>
        get() = _myExpenses

    private val _sumOfExpenses = MutableStateFlow<Int>(0)
    val sumOfExpenses: StateFlow<Int>
        get() = _sumOfExpenses

    init {
        getExpensesList()
    }

    private fun getExpensesList() {
        _myExpenses.value = getExpensesListUseCase()
        _sumOfExpenses.value = myExpenses.value.sumOf { it.amount }
    }

}