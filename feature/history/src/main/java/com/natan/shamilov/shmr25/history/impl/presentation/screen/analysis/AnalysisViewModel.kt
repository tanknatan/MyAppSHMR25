package com.natan.shamilov.shmr25.history.impl.presentation.screen.analysis

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.common.api.HapticProvider
import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.common.impl.domain.entity.HistoryType
import com.natan.shamilov.shmr25.common.impl.domain.entity.State
import com.natan.shamilov.shmr25.common.impl.domain.entity.Transaction
import com.natan.shamilov.shmr25.common.impl.presentation.BaseViewModel
import com.natan.shamilov.shmr25.common.impl.presentation.utils.normalizePercentages
import com.natan.shamilov.shmr25.common.impl.presentation.utils.toEndOfDayIso
import com.natan.shamilov.shmr25.common.impl.presentation.utils.toStartOfDayIso
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
import javax.inject.Inject

class AnalysisViewModel @Inject constructor(
    private val getHistoryByPeriodUseCase: GetHistoryByPeriodUseCase,
    private val hapticProvider: HapticProvider,
) : BaseViewModel(hapticProvider) {

    private val _analyticsUiModel = MutableStateFlow<AnalyticsUiModel?>(null)
    val analyticsUiModel: StateFlow<AnalyticsUiModel?> = _analyticsUiModel.asStateFlow()

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

            val startDate = toStartOfDayIso(startLocalDate.toString())
            val endDate = toEndOfDayIso(endLocalDate.toString())

            when (
                val historyListResult = getHistoryByPeriodUseCase(
                    startDate = startDate,
                    endDate = endDate,
                    isIncome = historyType == HistoryType.INCOME
                )
            ) {
                is Result.Error -> {
                    _uiState.value = State.Error
                    Log.e("AnalysisViewModel", "Ошибка загрузки истории: ${historyListResult.exception.message}")
                }

                Result.Loading -> {
                    _uiState.value = State.Loading
                }

                is Result.Success<List<Transaction>> -> {
                    val items = historyListResult.data
                    val totalAmount = items.sumOf { it.amount }

                    val grouped = items
                        .groupBy { Triple(it.categoryId, it.name, it.emoji) }
                        .map { (key, group) ->
                            key to group.sumOf { it.amount }
                        }

                    val normalized = normalizePercentages(grouped, totalAmount)

                    val categoryStats = normalized
                        .map { (key, amount, percent) ->
                            val (categoryId, name, emoji) = key
                            CategoryStatUiModel(
                                categoryId = categoryId,
                                categoryName = name,
                                amount = amount,
                                percent = percent,
                                emoji = emoji
                            )
                        }
                        .sortedByDescending { it.percent }

                    _analyticsUiModel.value = AnalyticsUiModel(
                        totalAmount = totalAmount,
                        categoryStats = categoryStats
                    )

                    _uiState.value = State.Content
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("AnalysisViewModel", "ViewModel уничтожен, отменяем все задачи")
    }
}
