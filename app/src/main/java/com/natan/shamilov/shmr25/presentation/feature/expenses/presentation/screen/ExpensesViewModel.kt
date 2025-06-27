package com.natan.shamilov.shmr25.presentation.feature.expenses.presentation.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.common.State
import com.natan.shamilov.shmr25.data.api.NetworkStateReceiver
import com.natan.shamilov.shmr25.data.api.Result
import com.natan.shamilov.shmr25.domain.entity.Expense
import com.natan.shamilov.shmr25.presentation.feature.expenses.domain.usecase.GetExpensesListUseCase
import com.natan.shamilov.shmr25.presentation.feature.expenses.domain.usecase.LoadExpensesByPeriodUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ExpensesViewModel @Inject constructor(
    private val getExpensesListUseCase: GetExpensesListUseCase,
    private val loadExpensesByPeriodUseCase: LoadExpensesByPeriodUseCase,
    private val networkStateReceiver: NetworkStateReceiver
) : ViewModel() {
    private val _expenses = MutableStateFlow<List<Expense>>(emptyList())
    val expenses: StateFlow<List<Expense>> = _expenses.asStateFlow()

    private val _sumOfExpenses = MutableStateFlow(0.0)
    val sumOfExpenses: StateFlow<Double> = _sumOfExpenses.asStateFlow()

    private val _uiState = MutableStateFlow<State>(State.Loading)
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    private var dataLoadingJob: Job? = null
    private var networkJob: Job? = null

    init {
        networkJob = viewModelScope.launch {
            networkStateReceiver.isNetworkAvailable.collect { isAvailable ->
                if (isAvailable && _uiState.value == State.Error) {
                    loadExpenses()
                }
            }
        }
    }

    fun initialize() {
        loadExpenses()
    }

    private fun loadExpenses() {
        dataLoadingJob?.cancel()
        
        dataLoadingJob = viewModelScope.launch {
            try {
                _uiState.value = State.Loading
                
                val today = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
                
                when (val result = loadExpensesByPeriodUseCase(today, today)) {
                    is Result.Success -> {
                        val expenses = result.data
                        if (expenses.isEmpty()) {
                            _expenses.value = emptyList()
                            _sumOfExpenses.value = 0.0
                            _uiState.value = State.Content // Пустой список - это валидное состояние для расходов
                            Log.d("ExpensesViewModel", "Список расходов пуст за сегодня")
                        } else {
                            _expenses.value = expenses
                            _sumOfExpenses.value = expenses.sumOf { it.amount }
                            _uiState.value = State.Content
                        }
                    }
                    is Result.Error -> {
                        // Пробуем получить данные из локальной БД
                        val cachedExpenses = getExpensesListUseCase()
                        if (cachedExpenses.isNotEmpty()) {
                            _expenses.value = cachedExpenses
                            _sumOfExpenses.value = cachedExpenses.sumOf { it.amount }
                            _uiState.value = State.Content
                            Log.w("ExpensesViewModel", "Используем кэшированные данные: ${result.exception.message}")
                        } else {
                            _uiState.value = State.Error
                            Log.e("ExpensesViewModel", "Ошибка загрузки расходов: ${result.exception.message}")
                        }
                    }
                    is Result.Loading -> {
                        _uiState.value = State.Loading
                    }
                }
            } catch (e: Exception) {
                if (e is kotlinx.coroutines.CancellationException) throw e
                _uiState.value = State.Error
                Log.e("ExpensesViewModel", "Неожиданная ошибка при загрузке расходов", e)
            }
        }
    }

    fun loadDataInBackground() {
        dataLoadingJob?.cancel()
        dataLoadingJob = viewModelScope.launch {
            try {
                val today = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
                
                when (val result = loadExpensesByPeriodUseCase(today, today)) {
                    is Result.Success -> {
                        val expenses = result.data
                        _expenses.value = expenses
                        _sumOfExpenses.value = expenses.sumOf { it.amount }
                    }
                    is Result.Error -> {
                        Log.w("ExpensesViewModel", "Ошибка фоновой загрузки: ${result.exception.message}")
                    }
                    is Result.Loading -> {
                        // Игнорируем состояние загрузки при фоновом обновлении
                    }
                }
            } catch (e: Exception) {
                if (e is kotlinx.coroutines.CancellationException) throw e
                Log.e("ExpensesViewModel", "Ошибка при фоновой загрузке", e)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("ExpensesViewModel", "ViewModel уничтожен, отменяем все задачи")
        dataLoadingJob?.cancel()
        networkJob?.cancel()
    }
}

