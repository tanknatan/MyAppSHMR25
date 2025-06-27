package com.natan.shamilov.shmr25.presentation.feature.incomes.presentation.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.common.State
import com.natan.shamilov.shmr25.data.api.NetworkStateReceiver
import com.natan.shamilov.shmr25.domain.entity.Income
import com.natan.shamilov.shmr25.domain.usecase.CheckAccAndTransactionDataLoadingUseCase
import com.natan.shamilov.shmr25.domain.usecase.GetIncomesListUseCase
import com.natan.shamilov.shmr25.domain.usecase.LoadIncomesByPeriodUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class IncomesViewModel @Inject constructor(
    private val getIncomesListUseCase: GetIncomesListUseCase,
    private val checkAccAndTransactionDataLoadingUseCase: CheckAccAndTransactionDataLoadingUseCase,
    private val networkStateReceiver: NetworkStateReceiver
) : ViewModel() {

    private val _myIncomes = MutableStateFlow<List<Income>>(emptyList())
    val myIncomes: StateFlow<List<Income>>
        get() = _myIncomes

    private val _sumOfIncomes = MutableStateFlow<Double>(0.0)
    val sumOfIncomes: StateFlow<Double>
        get() = _sumOfIncomes

    private val _uiState = MutableStateFlow<State>(State.Loading)
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    private var dataLoadingJob: Job? = null
    private var networkJob: Job? = null

    init {
        // Только подписываемся на сеть, но не загружаем данные сразу
        networkJob = viewModelScope.launch {
            networkStateReceiver.isNetworkAvailable.collect { isAvailable ->
                if (isAvailable && _uiState.value == State.Error) {
                    getIncomesList()
                }
            }
        }
    }

    fun initialize() {
        // Загружаем данные только при первом показе экрана
        getIncomesList()
    }

    private fun getIncomesList() {
        // Отменяем предыдущую задачу если она существует
        dataLoadingJob?.cancel()
        dataLoadingJob = viewModelScope.launch(Dispatchers.IO) {
            checkAccAndTransactionDataLoadingUseCase().collect {
                _uiState.value = it

                when (it) {
                    State.Content -> {
                        val incomes = getIncomesListUseCase()
                        val sum = incomes.sumOf { it.amount }
                        withContext(Dispatchers.Main) {
                            _myIncomes.value = incomes
                            _sumOfIncomes.value = sum
                            _uiState.value = State.Content
                        }
                    }
                    State.Error -> {
                    }
                    State.Loading -> {
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("IncomesViewModel", "ViewModel уничтожен, отменяем все задачи")
        dataLoadingJob?.cancel()
        networkJob?.cancel()
    }
}
