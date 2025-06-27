package com.natan.shamilov.shmr25.feature.history.presentation.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.common.State
import com.natan.shamilov.shmr25.app.data.api.NetworkStateReceiver
import com.natan.shamilov.shmr25.app.data.api.Result
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
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getHistoryByPeriodUseCase: GetHistoryByPeriodUseCase,
    private val networkStateReceiver: NetworkStateReceiver
) : ViewModel() {

    private val _historyData = MutableStateFlow<HistoryData?>(null)
    val historyData: StateFlow<HistoryData?> = _historyData.asStateFlow()

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

    init {
        networkJob = viewModelScope.launch {
            networkStateReceiver.isNetworkAvailable.collect { isAvailable ->
                if (isAvailable && _uiState.value == State.Error) {
                    loadHistory()
                }
            }
        }
    }

    fun initialize() {
        loadHistory()
    }

    fun updatePeriod(startDate: Long, endDate: Long) {
        _selectedPeriodStart.value = startDate
        _selectedPeriodEnd.value = endDate
        loadHistory()
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

    override fun onCleared() {
        super.onCleared()
        Log.d("HistoryViewModel", "ViewModel уничтожен, отменяем все задачи")
        dataLoadingJob?.cancel()
        networkJob?.cancel()
    }
}
