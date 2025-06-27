package com.natan.shamilov.shmr25.presentation.feature.history.presentation.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.common.HistoryScreenEntity
import com.natan.shamilov.shmr25.common.State
import com.natan.shamilov.shmr25.data.api.NetworkStateReceiver
import com.natan.shamilov.shmr25.data.api.Result
import com.natan.shamilov.shmr25.domain.entity.Expense
import com.natan.shamilov.shmr25.domain.usecase.ChekcExpensesByPeriodLoadingUseCase
import com.natan.shamilov.shmr25.domain.usecase.LoadExpensesByPeriodUseCase
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
    private val networkStateReceiver: NetworkStateReceiver,
    private val chekcExpensesByPeriodLoadingUseCase: ChekcExpensesByPeriodLoadingUseCase,
) : ViewModel() {

    private val _myExpensesByPeriod = MutableStateFlow<List<HistoryScreenEntity>>(emptyList())
    val myExpensesByPeriod: StateFlow<List<HistoryScreenEntity>> = _myExpensesByPeriod.asStateFlow()

    private val _sumOfExpensesByPeriod = MutableStateFlow<Double>(0.0)
    val sumOfExpensesByPeriod: StateFlow<Double> = _sumOfExpensesByPeriod.asStateFlow()

    private val _uiState = MutableStateFlow<State>(State.Loading)
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    // Управление датами
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

    init {
        // Только подписываемся на сеть, но не загружаем данные сразу
        networkJob = viewModelScope.launch {
            networkStateReceiver.isNetworkAvailable.collect { isAvailable ->
                if (isAvailable && _uiState.value == State.Error) {
                    loadExpensesByPeriod(formattedStartDate, formattedEndDate)
                }
            }
        }
    }

    fun initialize() {
        Log.d("ExpensesHistoryViewModel", "startDateMillis: $startDateMillis, endDateMillis: $endDateMillis")
        Log.d("ExpensesHistoryViewModel", "startDateMillis: $formattedStartDate, endDateMillis: $formattedEndDate")
        loadExpensesByPeriod(formattedStartDate, formattedEndDate)
    }

    fun updateStartDate(dateMillis: Long) {
        _startDateMillis.value = dateMillis
        loadExpensesByPeriod(formattedStartDate, formattedEndDate)
    }

    fun updateEndDate(dateMillis: Long) {
        _endDateMillis.value = dateMillis
        loadExpensesByPeriod(formattedStartDate, formattedEndDate)
    }

    fun loadExpensesByPeriod(
        startDate: String,
        endDate: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = loadExpensesByPeriodUseCase(
                startDate = startDate,
                endDate = endDate
            )
            when (result) {
                is Result.Error -> {
                    Log.d("ExpensesHistoryViewModel", "${result.exception}.")
                    _uiState.value = State.Error
                }

                is Result.Loading -> {
                }

                is Result.Success<List<Expense>> -> {
                    Log.d("ExpensesHistoryViewModel", "Загружены расходы за период")
                    _myExpensesByPeriod.value = result.data
                    _sumOfExpensesByPeriod.value = myExpensesByPeriod.value.sumOf { it.amount }
                    _uiState.value = State.Content
                }
            }
        }
    }

    private fun initializeStartDate(): Long {
        val calendar = Calendar.getInstance()
        // Устанавливаем на 1-е число текущего месяца
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    private fun initializeEndDate(): Long {
        val calendar = Calendar.getInstance()
        // Устанавливаем на текущий день
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        return calendar.timeInMillis
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("ExpensesHistoryViewModel", "ViewModel уничтожен, отменяем все задачи")
    }
}
