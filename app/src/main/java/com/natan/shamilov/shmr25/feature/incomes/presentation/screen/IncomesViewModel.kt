package com.natan.shamilov.shmr25.feature.incomes.presentation.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.app.data.api.NetworkStateReceiver
import com.natan.shamilov.shmr25.common.data.model.Result
import com.natan.shamilov.shmr25.common.domain.entity.State
import com.natan.shamilov.shmr25.feature.incomes.domain.entity.Income
import com.natan.shamilov.shmr25.feature.incomes.domain.usecase.GetIncomesListUseCase
import com.natan.shamilov.shmr25.feature.incomes.domain.usecase.LoadIncomesByPeriodUseCase
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * ViewModel для экрана доходов.
 * Ответственность: Управление данными и состоянием UI для отображения списка доходов,
 * включая загрузку доходов за определенный период, обработку сетевых ошибок
 * и обновление данных при изменении состояния сети.
 */
class IncomesViewModel @Inject constructor(
    private val getIncomesListUseCase: GetIncomesListUseCase,
    private val loadIncomesByPeriodUseCase: LoadIncomesByPeriodUseCase,
    private val networkStateReceiver: NetworkStateReceiver,
) : ViewModel() {

    private val _incomes = MutableStateFlow<List<Income>>(emptyList())
    val incomes: StateFlow<List<Income>> = _incomes.asStateFlow()

    private val _sumOfIncomes = MutableStateFlow(0.0)
    val sumOfIncomes: StateFlow<Double> = _sumOfIncomes.asStateFlow()

    private val _uiState = MutableStateFlow<State>(State.Loading)
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            networkStateReceiver.isNetworkAvailable.collect { isAvailable ->
                if (isAvailable && _uiState.value == State.Error) {
                    loadIncomes()
                }
            }
        }
    }

    fun initialize() {
        loadIncomes()
    }

    private fun loadIncomes() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = State.Loading

            val today = LocalDate.now().format(DateTimeFormatter.ISO_DATE)

            when (val result = loadIncomesByPeriodUseCase(today, today)) {
                is Result.Success -> {
                    val incomes = result.data
                    if (incomes.isEmpty()) {
                        _uiState.value = State.Content
                        Log.d("IncomesViewModel", "Список доходов пуст")
                    } else {
                        _incomes.value = incomes
                        _sumOfIncomes.value = incomes.sumOf { it.amount }
                        _uiState.value = State.Content
                    }
                }

                is Result.Error -> {
                    val incomes = getIncomesListUseCase()
                    if (incomes.isNotEmpty()) {
                        _incomes.value = incomes
                        _sumOfIncomes.value = incomes.sumOf { it.amount }
                        _uiState.value = State.Content
                        Log.w("IncomesViewModel", "Используем кэшированные данные: ${result.exception.message}")
                    } else {
                        _uiState.value = State.Error
                        Log.e("IncomesViewModel", "Ошибка загрузки доходов: ${result.exception.message}")
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
        Log.d("IncomesViewModel", "ViewModel уничтожен, отменяем все задачи")
    }
}
