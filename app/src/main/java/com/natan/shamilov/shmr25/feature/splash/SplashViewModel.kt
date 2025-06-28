package com.natan.shamilov.shmr25.feature.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.app.data.api.Result
import com.natan.shamilov.shmr25.feature.account.domain.usecase.LoadAccountsListUseCase
import com.natan.shamilov.shmr25.feature.expenses.domain.repository.ExpensesRepository
import com.natan.shamilov.shmr25.feature.incomes.domain.repository.IncomesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel для сплэш-экрана.
 * Ответственность: Управление начальной загрузкой данных приложения,
 * включая загрузку списка счетов и транзакций. Обеспечивает параллельную
 * загрузку данных во время отображения сплэш-экрана.
 */
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
        dataLoadingJob?.cancel()
        dataLoadingJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                when (val accountsResult = loadAccountsListUseCase()) {
                    is Result.Success -> {
                        loadTransactionsForAccounts()
                        _uiState.value = true
                    }
                    is Result.Error -> {
                        Log.e("SplashViewModel", "Ошибка загрузки аккаунтов: ${accountsResult.exception.message}")
                        _uiState.value = true
                    }
                    is Result.Loading -> {
                    }
                }
            } catch (e: Exception) {
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
