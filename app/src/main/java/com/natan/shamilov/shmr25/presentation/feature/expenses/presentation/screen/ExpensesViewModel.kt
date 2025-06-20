package com.natan.shamilov.shmr25.presentation.feature.expenses.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.commo.State
import com.natan.shamilov.shmr25.domain.entity.Expense
import com.natan.shamilov.shmr25.domain.usecase.GetExpensesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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



    private val _uiState = MutableStateFlow<State>(State.Loading)

    val uiState: StateFlow<State> = _uiState.asStateFlow()

    init {
        getExpensesList()
    }



    private fun getExpensesList() {
        viewModelScope.launch {
            _myExpenses.value = getExpensesListUseCase()
            _sumOfExpenses.value = myExpenses.value.sumOf { it.amount }
            _uiState.value = State.Content
        }
    }
}