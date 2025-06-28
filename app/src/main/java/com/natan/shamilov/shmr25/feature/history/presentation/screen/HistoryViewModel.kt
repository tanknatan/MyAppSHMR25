package com.natan.shamilov.shmr25.feature.history.presentation.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.app.data.api.NetworkStateReceiver
import com.natan.shamilov.shmr25.app.data.api.Result
import com.natan.shamilov.shmr25.common.State
import com.natan.shamilov.shmr25.feature.history.domain.HistoryType
import com.natan.shamilov.shmr25.feature.history.domain.model.HistoryData
import com.natan.shamilov.shmr25.feature.history.domain.usecase.GetHistoryByPeriodUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
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
 * Модель данных для отображения на UI
 */
data class HistoryUiModel(
    val items: List<HistoryItem>,
    val totalAmount: Double
)

/**
 * Элемент истории для отображения
 */
data class HistoryItem(
    val id: Long,
    val title: String,
    val amount: Double,
    val time: String,
    val emoji: String,
    val comment: String?
)

/**
 * ViewModel для экрана истории транзакций.
 * Ответственность: Управление данными и состоянием UI для отображения истории транзакций,
 * включая загрузку транзакций за выбранный период, обработку сетевых ошибок,
 * и управление выбором периода для фильтрации.
 */
@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getHistoryByPeriodUseCase: GetHistoryByPeriodUseCase,
    private val networkStateReceiver: NetworkStateReceiver
) : ViewModel() {

    private val _historyData = MutableStateFlow<HistoryData?>(null)
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

    private var dataLoadingJob: Job? = null
    private var networkJob: Job? = null
    private var historyType: HistoryType = HistoryType.EXPENSE

    init {
        networkJob = viewModelScope.launch {
            networkStateReceiver.isNetworkAvailable.collect { isAvailable ->
                if (isAvailable && _uiState.value == State.Error) {
                    loadHistory()
                }
            }
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
        dataLoadingJob?.cancel()

        dataLoadingJob = viewModelScope.launch {
            try {
                _uiState.value = State.Loading

                val startLocalDate = Instant.ofEpochMilli(_selectedPeriodStart.value)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
                val endLocalDate = Instant.ofEpochMilli(_selectedPeriodEnd.value)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()

                val startDate = startLocalDate.format(DateTimeFormatter.ISO_DATE)
                val endDate = endLocalDate.format(DateTimeFormatter.ISO_DATE)

                when (val result = getHistoryByPeriodUseCase(startDate, endDate)) {
                    is Result.Success -> {
                        _historyData.value = result.data
                        updateUiModel(result.data)
                        _uiState.value = State.Content
                    }
                    is Result.Error -> {
                        _uiState.value = State.Error
                        Log.e("HistoryViewModel", "Ошибка загрузки истории: ${result.exception.message}")
                    }
                    is Result.Loading -> {
                        _uiState.value = State.Loading
                    }
                }
            } catch (e: Exception) {
                if (e is kotlinx.coroutines.CancellationException) throw e
                _uiState.value = State.Error
                Log.e("HistoryViewModel", "Неожиданная ошибка при загрузке истории", e)
            }
        }
    }

    private fun updateUiModel(data: HistoryData) {
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        val isoFormatter = DateTimeFormatter.ISO_DATE_TIME

        val (items, total) = when (historyType) {
            HistoryType.EXPENSE -> {
                val expenseItems = data.expenses.map { expense ->
                    HistoryItem(
                        id = expense.id,
                        title = expense.category.name,
                        amount = -expense.amount,
                        time = LocalDateTime.parse(expense.createdAt, isoFormatter).format(timeFormatter),
                        emoji = expense.category.emoji,
                        comment = expense.comment
                    )
                }
                Pair(expenseItems, -data.totalExpenses)
            }
            HistoryType.INCOME -> {
                val incomeItems = data.incomes.map { income ->
                    HistoryItem(
                        id = income.id,
                        title = income.category.name,
                        amount = income.amount,
                        time = LocalDateTime.parse(income.createdAt, isoFormatter).format(timeFormatter),
                        emoji = income.category.emoji,
                        comment = income.comment
                    )
                }
                Pair(incomeItems, data.totalIncomes)
            }
        }

        _historyUiModel.value = HistoryUiModel(items, total)
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("HistoryViewModel", "ViewModel уничтожен, отменяем все задачи")
        dataLoadingJob?.cancel()
        networkJob?.cancel()
    }
}
