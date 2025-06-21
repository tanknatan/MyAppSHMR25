package com.natan.shamilov.shmr25.presentation.feature.expenses.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.commo.State
import com.natan.shamilov.shmr25.data.api.Result
import com.natan.shamilov.shmr25.domain.entity.Expense
import com.natan.shamilov.shmr25.domain.usecase.CheckAccAndTransactionDataLoadingUseCase
import com.natan.shamilov.shmr25.domain.usecase.GetExpensesListUseCase
import com.natan.shamilov.shmr25.domain.usecase.LoadExpensesByPeriodUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ExpensesViewModel @Inject constructor(
    private val getExpensesListUseCase: GetExpensesListUseCase,
    private val checkAccAndTransactionDataLoadingUseCase: CheckAccAndTransactionDataLoadingUseCase,
    private val loadExpensesByPeriodUseCase: LoadExpensesByPeriodUseCase,
) : ViewModel() {
    private val _myExpenses = MutableStateFlow<List<Expense>>(emptyList())
    val myExpenses: StateFlow<List<Expense>>
        get() = _myExpenses

    private val _myExpensesByPeriod = MutableStateFlow<List<Expense>>(emptyList())
    val myExpensesByPeriod: StateFlow<List<Expense>>
        get() = _myExpensesByPeriod

    private val _sumOfExpenses = MutableStateFlow<Double>(0.0)
    val sumOfExpenses: StateFlow<Double>
        get() = _sumOfExpenses

    private val _uiState = MutableStateFlow<State>(State.Loading)

    val uiState: StateFlow<State> = _uiState.asStateFlow()

    init {
        getExpensesList()
    }

    private fun getExpensesList() {
        viewModelScope.launch {

            checkAccAndTransactionDataLoadingUseCase().collect {
                _uiState.value = it
                when (it) {
                    State.Content -> {
                        _myExpenses.value = getExpensesListUseCase()
                        _sumOfExpenses.value = myExpenses.value.sumOf { it.amount }
                    }

                    else -> Unit
                }
            }
        }
    }

    fun loadExpensesByPeriod(
        startDate: String,
        endDate: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = loadExpensesByPeriodUseCase(
                startDate = startDate,
                endDate = endDate
            )
            when (result) {
                is Result.Error -> {
                    _uiState.value = State.Error
                }

                Result.Loading -> {
                    _uiState.value = State.Loading
                }

                is Result.Success<List<Expense>> -> {
                    _myExpensesByPeriod.value = result.data
                }
            }
        }

    }
}