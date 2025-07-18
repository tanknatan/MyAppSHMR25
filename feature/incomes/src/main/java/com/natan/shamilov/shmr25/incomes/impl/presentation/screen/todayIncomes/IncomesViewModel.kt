package com.natan.shamilov.shmr25.incomes.impl.presentation.screen.todayIncomes

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.common.impl.domain.entity.State
import com.natan.shamilov.shmr25.common.impl.domain.entity.Transaction
import com.natan.shamilov.shmr25.incomes.impl.domain.usecase.LoadIncomesByPeriodUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel для экрана доходов.
 * Ответственность: Управление данными и состоянием UI для отображения списка доходов,
 * включая загрузку доходов за определенный период, обработку сетевых ошибок
 * и обновление данных при изменении состояния сети.
 */
class IncomesViewModel @Inject constructor(
    private val loadIncomesByPeriodUseCase: LoadIncomesByPeriodUseCase,
) : ViewModel() {

    private val _incomes = MutableStateFlow<List<Transaction>>(emptyList())
    val incomes: StateFlow<List<Transaction>> = _incomes.asStateFlow()

    private val _sumOfIncomes = MutableStateFlow(0.0)
    val sumOfIncomes: StateFlow<Double> = _sumOfIncomes.asStateFlow()

    private val _uiState = MutableStateFlow<State>(State.Loading)
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    fun initialize() {
        loadIncomesList()
    }

    private fun loadIncomesList() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = State.Loading
            val result = loadIncomesByPeriodUseCase()
            Log.d("LoadIncomes", result.toString())
            when (result) {
                is Result.Error -> {
                }

                Result.Loading -> {
                }

                is Result.Success<List<Transaction>> -> {
                    _incomes.value = result.data
                    _sumOfIncomes.value = _incomes.value.sumOf { it.amount }
                    _uiState.value = State.Content
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("IncomesViewModel", "ViewModel уничтожен, отменяем все задачи")
    }
}
