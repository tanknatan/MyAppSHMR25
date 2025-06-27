package com.natan.shamilov.shmr25.presentation.feature.history.presentation.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.common.HistoryScreenEntity
import com.natan.shamilov.shmr25.common.State
import com.natan.shamilov.shmr25.data.api.NetworkStateReceiver
import com.natan.shamilov.shmr25.data.api.Result
import com.natan.shamilov.shmr25.domain.usecase.LoadExpensesByPeriodUseCase
import com.natan.shamilov.shmr25.domain.usecase.LoadIncomesByPeriodUseCase
import com.natan.shamilov.shmr25.presentation.feature.history.domain.HistoryType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val loadExpensesByPeriodUseCase: LoadExpensesByPeriodUseCase,
    private val loadIncomesByPeriodUseCase: LoadIncomesByPeriodUseCase,
    private val networkStateReceiver: NetworkStateReceiver
) : ViewModel() {

    private val _historyItems = MutableStateFlow<List<HistoryScreenEntity>>(emptyList())
    val historyItems: StateFlow<List<HistoryScreenEntity>> = _historyItems.asStateFlow()

    private val _sumOfItems = MutableStateFlow<Double>(0.0)
    val sumOfItems: StateFlow<Double> = _sumOfItems.asStateFlow()

    private val _uiState = MutableStateFlow<State>(State.Loading)
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    private val _startDateMillis = MutableStateFlow(initializeStartDate())
    val startDateMillis: StateFlow<Long> = _startDateMillis.asStateFlow()

    private val _endDateMillis = MutableStateFlow(initializeEndDate())
    val endDateMillis: StateFlow<Long> = _endDateMillis.asStateFlow()

    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    val formattedStartDate: String
        get() = dateFormatter.format(Date(_startDateMillis.value))

    val formattedEndDate: String
        get() = dateFormatter.format(Date(_endDateMillis.value))

    private var networkJob: Job? = null
    private lateinit var currentType: HistoryType

    fun initialize(type: HistoryType) {
        currentType = type
        setupNetworkMonitoring()
        loadHistory(formattedStartDate, formattedEndDate)
    }

    private fun setupNetworkMonitoring() {
        networkJob = viewModelScope.launch {
            networkStateReceiver.isNetworkAvailable.collect { isAvailable ->
                if (isAvailable && _uiState.value == State.Error) {
                    loadHistory(formattedStartDate, formattedEndDate)
                }
            }
        }
    }

    fun updateStartDate(dateMillis: Long) {
        _startDateMillis.value = dateMillis
        loadHistory(formattedStartDate, formattedEndDate)
    }

    fun updateEndDate(dateMillis: Long) {
        _endDateMillis.value = dateMillis
        loadHistory(formattedStartDate, formattedEndDate)
    }

    private fun loadHistory(
        startDate: String,
        endDate: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = State.Loading
            
            val result = when (currentType) {
                HistoryType.EXPENSE -> loadExpensesByPeriodUseCase(startDate, endDate)
                HistoryType.INCOME -> loadIncomesByPeriodUseCase(startDate, endDate)
            }

            when (result) {
                is Result.Error -> {
                    Log.e("HistoryViewModel", "Error loading history: ${result.exception}")
                    _uiState.value = State.Error
                }
                is Result.Loading -> {
                    _uiState.value = State.Loading
                }
                is Result.Success -> {
                    _historyItems.value = result.data
                    _sumOfItems.value = result.data.sumOf { it.amount }
                    _uiState.value = State.Content
                }
            }
        }
    }

    private fun initializeStartDate(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    private fun initializeEndDate(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        return calendar.timeInMillis
    }

    override fun onCleared() {
        super.onCleared()
        networkJob?.cancel()
    }
}
