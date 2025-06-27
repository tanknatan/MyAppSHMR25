package com.natan.shamilov.shmr25.presentation.feature.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.data.api.Result
import com.natan.shamilov.shmr25.domain.entity.Account
import com.natan.shamilov.shmr25.presentation.feature.account.domain.usecase.LoadAccountsListUseCase
import com.natan.shamilov.shmr25.presentation.feature.expenses.domain.repository.ExpensesRepository
import com.natan.shamilov.shmr25.presentation.feature.incomes.domain.repository.IncomesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val loadAccountsListUseCase: LoadAccountsListUseCase,
    private val expensesRepository: ExpensesRepository,
    private val incomesRepository: IncomesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(false)
    val uiState: StateFlow<Boolean> = _uiState.asStateFlow()

    private var dataLoadingJob: Job? = null

    init {
        Log.d("SplashViewModel", "Инициализация SplashViewModel")
        // Загружаем данные в фоне параллельно с анимацией
        loadDataInBackground()
    }

    private fun loadDataInBackground() {
        // Отменяем предыдущую задачу если она существует
        dataLoadingJob?.cancel()
        dataLoadingJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                // Загружаем список аккаунтов
                when (val accountsResult = loadAccountsListUseCase()) {
                    is Result.Success -> {
                        // Загружаем транзакции для всех аккаунтов
                        loadTransactionsForAccounts()
                        _uiState.value = true
                    }
                    is Result.Error -> {
                        Log.e("SplashViewModel", "Ошибка загрузки аккаунтов: ${accountsResult.exception.message}")
                        _uiState.value = true
                    }
                    is Result.Loading -> {
                        // Ничего не делаем, ждем результата
                    }
                }
            } catch (e: Exception) {
                Log.e("SplashViewModel", "Неожиданная ошибка при загрузке данных", e)
                _uiState.value = true
            }
        }
    }

    private suspend fun loadTransactionsForAccounts() {
        try {
            // Загружаем расходы
            when (val expensesResult = expensesRepository.loadTodayExpenses()) {
                is Result.Success -> {
                    Log.d("SplashViewModel", "Расходы успешно загружены")
                }
                is Result.Error -> {
                    Log.e("SplashViewModel", "Ошибка загрузки расходов: ${expensesResult.exception.message}")
                }
                is Result.Loading -> {
                    // Ничего не делаем, ждем результата
                }
            }

            // Загружаем доходы
            when (val incomesResult = incomesRepository.loadTodayIncomes()) {
                is Result.Success -> {
                    Log.d("SplashViewModel", "Доходы успешно загружены")
                }
                is Result.Error -> {
                    Log.e("SplashViewModel", "Ошибка загрузки доходов: ${incomesResult.exception.message}")
                }
                is Result.Loading -> {
                    // Ничего не делаем, ждем результата
                }
            }
        } catch (e: Exception) {
            Log.e("SplashViewModel", "Ошибка при загрузке транзакций", e)
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("SplashViewModel", "ViewModel уничтожен, отменяем все задачи")
        dataLoadingJob?.cancel()
    }
}
