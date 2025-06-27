package com.natan.shamilov.shmr25.presentation.feature.incomes.presentation.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.common.State
import com.natan.shamilov.shmr25.data.api.Result
import com.natan.shamilov.shmr25.data.api.NetworkStateReceiver
import com.natan.shamilov.shmr25.domain.entity.Income
import com.natan.shamilov.shmr25.domain.usecase.CheckAccAndTransactionDataLoadingUseCase
import com.natan.shamilov.shmr25.domain.usecase.GetIncomesListUseCase
import com.natan.shamilov.shmr25.domain.usecase.LoadIncomesByPeriodUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
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

@HiltViewModel
class IncomesHistoryViewModel @Inject constructor(
    private val getIncomesListUseCase: GetIncomesListUseCase,
    private val checkAccAndTransactionDataLoadingUseCase: CheckAccAndTransactionDataLoadingUseCase,
    private val loadIncomesByPeriodUseCase: LoadIncomesByPeriodUseCase,
    private val networkStateReceiver: NetworkStateReceiver
) : ViewModel() {

    private val _myIncomesByPeriod = MutableStateFlow<List<Income>>(emptyList())
    val myIncomesByPeriod: StateFlow<List<Income>>
        get() = _myIncomesByPeriod

    private val _sumOfIncomesByPeriod = MutableStateFlow<Double>(0.0)
    val sumOfIncomesByPeriod: StateFlow<Double>
        get() = _sumOfIncomesByPeriod

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

    private var dataLoadingJob: Job? = null
    private var networkJob: Job? = null

    init {
        // Только подписываемся на сеть, но не загружаем данные сразу
        networkJob = viewModelScope.launch {
            networkStateReceiver.isNetworkAvailable.collect { isAvailable ->
                if (isAvailable && _uiState.value == State.Error) {
                    loadIncomesByPeriod(formattedStartDate, formattedEndDate)
                }
            }
        }
    }

    fun initialize() {
        // Загружаем данные только при первом показе экрана
        loadIncomesByPeriod(formattedStartDate, formattedEndDate)
    }

    fun loadIncomesByPeriod(
        startDate: String,
        endDate: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = loadIncomesByPeriodUseCase(
                startDate = startDate,
                endDate = endDate
            )
            when (result) {
                is Result.Error -> {
                    _uiState.value = State.Error
                }

                Result.Loading -> {
                    _uiState.value = State.Loading
                }
                is Result.Success<List<Income>> -> {
                    _uiState.value = State.Content
                    _myIncomesByPeriod.value = result.data
                    _sumOfIncomesByPeriod.value = myIncomesByPeriod.value.sumOf { it.amount }
                }
            }
        }
    }

    fun updateStartDate(dateMillis: Long) {
        _startDateMillis.value = dateMillis
        loadIncomesByPeriod(formattedStartDate, formattedEndDate)
    }

    fun updateEndDate(dateMillis: Long) {
        _endDateMillis.value = dateMillis
        loadIncomesByPeriod(formattedStartDate, formattedEndDate)
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
        Log.d("IncomesViewModel", "ViewModel уничтожен, отменяем все задачи")
        dataLoadingJob?.cancel()
        networkJob?.cancel()
    }
}
