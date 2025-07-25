package com.natan.shamilov.shmr25.expenses.impl.presentation.screen.todayExpenses

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.common.api.HapticProvider
import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.common.impl.domain.entity.State
import com.natan.shamilov.shmr25.common.impl.domain.entity.Transaction
import com.natan.shamilov.shmr25.common.impl.presentation.BaseViewModel
import com.natan.shamilov.shmr25.expenses.impl.domain.usecase.LoadExpensesByPeriodUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel для экрана расходов.
 * Ответственность: Управление данными и состоянием UI для отображения списка расходов,
 * включая загрузку расходов за определенный период, обработку сетевых ошибок
 * и обновление данных при изменении состояния сети.
 */
class ExpensesViewModel @Inject constructor(
    private val loadExpensesByPeriodUseCase: LoadExpensesByPeriodUseCase,
    private val hapticProvider: HapticProvider,
) : BaseViewModel(hapticProvider) {
    private val _expenses = MutableStateFlow<List<Transaction>>(emptyList())
    val expenses: StateFlow<List<Transaction>> = _expenses.asStateFlow()

    private val _sumOfExpenses = MutableStateFlow(0.0)
    val sumOfExpenses: StateFlow<Double> = _sumOfExpenses.asStateFlow()

    private val _uiState = MutableStateFlow<State>(State.Loading)
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    fun initialize() {
        loadExpensesList()
        Log.d("ExpensesViewModel", "start")
    }

    private fun loadExpensesList() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = State.Loading
            val result = loadExpensesByPeriodUseCase()
            Log.d("LoadExpenses", result.toString())
            when (result) {
                is Result.Error -> {
                    _uiState.value = State.Error
                }

                Result.Loading -> {
                    _uiState.value = State.Loading
                }

                is Result.Success<List<Transaction>> -> {
                    _expenses.value = result.data
                    _sumOfExpenses.value = _expenses.value.sumOf { it.amount }
                    _uiState.value = State.Content
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("ExpensesViewModel", "ViewModel уничтожен, отменяем все задачи")
    }
}
