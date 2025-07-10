package com.natan.shamilov.shmr25.feature.expenses.presentation.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.common.network.NetworkStateReceiver
import com.natan.shamilov.shmr25.common.data.model.Result
import com.natan.shamilov.shmr25.common.domain.entity.State
import com.natan.shamilov.shmr25.feature.expenses.domain.entity.Expense
import com.natan.shamilov.shmr25.feature.expenses.domain.usecase.GetExpensesListUseCase
import com.natan.shamilov.shmr25.feature.expenses.domain.usecase.LoadExpensesByPeriodUseCase
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * ViewModel для экрана расходов.
 * Ответственность: Управление данными и состоянием UI для отображения списка расходов,
 * включая загрузку расходов за определенный период, обработку сетевых ошибок
 * и обновление данных при изменении состояния сети.
 */
class ExpensesViewModel @Inject constructor(
    private val getExpensesListUseCase: GetExpensesListUseCase,
    private val loadExpensesByPeriodUseCase: LoadExpensesByPeriodUseCase,
    private val networkStateReceiver: NetworkStateReceiver,
) : ViewModel() {
    private val _expenses = MutableStateFlow<List<Expense>>(emptyList())
    val expenses: StateFlow<List<Expense>> = _expenses.asStateFlow()

    private val _sumOfExpenses = MutableStateFlow(0.0)
    val sumOfExpenses: StateFlow<Double> = _sumOfExpenses.asStateFlow()

    private val _uiState = MutableStateFlow<State>(State.Loading)
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
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
        viewModelScope.launch(Dispatchers.IO) {
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
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("ExpensesViewModel", "ViewModel уничтожен, отменяем все задачи")
    }
}
