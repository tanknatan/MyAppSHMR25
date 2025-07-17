package com.natan.shamilov.shmr25.history.impl.presentation.screen.history

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.common.impl.domain.entity.HistoryType
import com.natan.shamilov.shmr25.common.impl.domain.entity.State
import com.natan.shamilov.shmr25.common.impl.domain.entity.Transaction
import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.history.impl.domain.usecase.GetHistoryByPeriodUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

/**
 * ViewModel для экрана истории транзакций.
 * Ответственность: Управление данными и состоянием UI для отображения истории транзакций,
 * включая загрузку транзакций за выбранный период, обработку сетевых ошибок,
 * и управление выбором периода для фильтрации.
 */
class HistoryViewModel @Inject constructor(
    private val getHistoryByPeriodUseCase: GetHistoryByPeriodUseCase,
) : ViewModel() {

    private val _historyUiModel = MutableStateFlow<HistoryUiModel?>(null)
    val historyUiModel: StateFlow<HistoryUiModel?> = _historyUiModel.asStateFlow()

    private val _uiState = MutableStateFlow<State>(State.Loading)
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    private val _selectedPeriodStart = MutableStateFlow(
        LocalDate.now().withDayOfMonth(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    )
    val selectedPeriodStart: StateFlow<Long> = _selectedPeriodStart.asStateFlow()

    private val _selectedPeriodEnd = MutableStateFlow(
        LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    )
    val selectedPeriodEnd: StateFlow<Long> = _selectedPeriodEnd.asStateFlow()

    private var historyType: HistoryType = HistoryType.EXPENSE

    init {
        viewModelScope.launch {
//            networkStateReceiver.isNetworkAvailable.collect { isAvailable ->
//                if (isAvailable && _uiState.value == State.Error) {
//                    loadHistory()
//                }
//            }
        }
    }

    fun initialize(type: HistoryType) {
        historyType = type
        loadHistory()
    }

    fun updatePeriod(startDate: Long, endDate: Long) {
        _selectedPeriodStart.value = startDate
        _selectedPeriodEnd.value = endDate
        loadHistory()
    }

    /**
     * Очищает дату начала периода, устанавливая её на начало текущего месяца
     */
    fun clearStartDate() {
        val startOfMonth = LocalDate.now()
            .withDayOfMonth(1)
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        updatePeriod(startOfMonth, _selectedPeriodEnd.value)
    }

    /**
     * Очищает дату конца периода, устанавливая её на текущий момент
     */
    fun clearEndDate() {
        val now = LocalDateTime.now()
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        updatePeriod(_selectedPeriodStart.value, now)
    }

    private fun loadHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = State.Loading

            val startLocalDate = Instant.ofEpochMilli(_selectedPeriodStart.value)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
            val endLocalDate = Instant.ofEpochMilli(_selectedPeriodEnd.value)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()

            val startDate = startLocalDate.format(DateTimeFormatter.ISO_DATE)
            val endDate = endLocalDate.format(DateTimeFormatter.ISO_DATE)
            when (
                val historyListResult = getHistoryByPeriodUseCase(
                    startDate = startDate,
                    endDate = endDate,
                    isIncome = historyType == HistoryType.INCOME
                )
            ) {
                is Result.Error -> {
                    _uiState.value = State.Error
                    Log.e("HistoryViewModel", "Ошибка загрузки истории: ${historyListResult.exception.message}")
                }

                Result.Loading -> {
                    _uiState.value = State.Loading
                }

                is Result.Success<List<Transaction>> -> {
                    _historyUiModel.value = HistoryUiModel(
                        items = historyListResult.data,
                        totalAmount = historyListResult.data.sumOf { it.amount }
                    )
                    _uiState.value = State.Content
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("HistoryViewModel", "ViewModel уничтожен, отменяем все задачи")
    }
}
