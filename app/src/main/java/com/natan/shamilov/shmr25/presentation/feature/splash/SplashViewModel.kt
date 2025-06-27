package com.natan.shamilov.shmr25.presentation.feature.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.data.api.Result
import com.natan.shamilov.shmr25.domain.usecase.LoadAccountsListUseCase
import com.natan.shamilov.shmr25.domain.usecase.LoadTodayTransactionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val loadAccountsListUseCase: LoadAccountsListUseCase,
    private val loadTodayTransactionsUseCase: LoadTodayTransactionsUseCase,
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
            when (loadAccountsListUseCase()) {
                is Result.Success -> {
                    when (loadTodayTransactionsUseCase()) {
                        is Result.Success -> {
                            _uiState.value = true
                        }

                        is Result.Error -> {
                            _uiState.value = true
                        }

                        is Result.Loading -> {
                        }
                    }
                }

                is Result.Error -> {
                    _uiState.value = true
                }

                is Result.Loading -> {
                }
            }
        }
    }
    override fun onCleared() {
        super.onCleared()
        Log.d("SplashViewModel", "ViewModel уничтожен, отменяем все задачи")
    }
}
