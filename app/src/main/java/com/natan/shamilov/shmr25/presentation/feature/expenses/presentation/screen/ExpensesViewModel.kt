package com.natan.shamilov.shmr25.presentation.feature.expenses.presentation.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.common.State
import com.natan.shamilov.shmr25.data.api.Result
import com.natan.shamilov.shmr25.data.api.NetworkStateReceiver
import com.natan.shamilov.shmr25.domain.entity.Expense
import com.natan.shamilov.shmr25.domain.usecase.CheckAccAndTransactionDataLoadingUseCase
import com.natan.shamilov.shmr25.domain.usecase.GetExpensesListUseCase
import com.natan.shamilov.shmr25.domain.usecase.LoadAccountsListUseCase
import com.natan.shamilov.shmr25.domain.usecase.LoadTodayTransactionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class ExpensesViewModel @Inject constructor(
    private val getExpensesListUseCase: GetExpensesListUseCase,
    private val checkAccAndTransactionDataLoadingUseCase: CheckAccAndTransactionDataLoadingUseCase,
    private val networkStateReceiver: NetworkStateReceiver,
    private val loadAccountsListUseCase: LoadAccountsListUseCase,
    private val loadTodayTransactionsUseCase: LoadTodayTransactionsUseCase,
) : ViewModel() {
    private val _myExpenses = MutableStateFlow<List<Expense>>(emptyList())
    val myExpenses: StateFlow<List<Expense>>
        get() = _myExpenses

    private val _sumOfExpenses = MutableStateFlow<Double>(0.0)
    val sumOfExpenses: StateFlow<Double>
        get() = _sumOfExpenses

    private val _uiState = MutableStateFlow<State>(State.Loading)
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    private var dataLoadingJob: Job? = null
    private var networkJob: Job? = null

    init {
        // Только подписываемся на сеть, но не загружаем данные сразу
        networkJob = viewModelScope.launch {
            networkStateReceiver.isNetworkAvailable.collect { isAvailable ->
                if (isAvailable && _uiState.value == State.Error) {
                    getExpensesList()
                }
            }
        }
    }

    fun initialize() {
        getExpensesList()
    }

    private fun getExpensesList() {
        // Отменяем предыдущую задачу если она существует
        dataLoadingJob?.cancel()
        dataLoadingJob = viewModelScope.launch(Dispatchers.IO) {
            checkAccAndTransactionDataLoadingUseCase().collect {
                withContext(Dispatchers.Main) {
                    _uiState.value = it
                }
                when (it) {
                    State.Content -> {
                        val expenses = getExpensesListUseCase()
                        val sum = expenses.sumOf { it.amount }
                        withContext(Dispatchers.Main) {
                            _myExpenses.value = expenses
                            _sumOfExpenses.value = sum
                            _uiState.value = State.Content
                        }
                    }

                    State.Error -> {
                        // Обработка ошибки
                    }

                    State.Loading -> {
                        // Показываем загрузку
                    }
                }
            }
        }
    }
     fun loadDataInBackground() {
        // Отменяем предыдущую задачу если она существует
        dataLoadingJob?.cancel()
        dataLoadingJob = viewModelScope.launch(Dispatchers.IO) {
            when (loadAccountsListUseCase()) {
                is Result.Success -> {
                    when (loadTodayTransactionsUseCase()) {
                        is Result.Success -> {
                            getExpensesList()
                        }

                        is Result.Error -> {
                        }

                        is Result.Loading -> {
                            _uiState.value = State.Loading
                        }
                    }
                }

                is Result.Error -> {
                }

                is Result.Loading -> {
                    _uiState.value = State.Loading
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("ExpensesViewModel", "ViewModel уничтожен, отменяем все задачи")
        dataLoadingJob?.cancel()
        networkJob?.cancel()
    }
}
