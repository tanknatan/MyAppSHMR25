package com.natan.shamilov.shmr25.feature.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
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
class SplashViewModel @Inject constructor(
    private val accountStartupLoader: AccountStartupLoader,
) : ViewModel() {

    private val _uiState = MutableStateFlow(false)
    val uiState: StateFlow<Boolean> = _uiState.asStateFlow()

    init {
        Log.d("SplashViewModel", "Инициализация SplashViewModel")
        loadDataInBackground()
    }

    private fun loadDataInBackground() {
        viewModelScope.launch(Dispatchers.IO) {
            accountStartupLoader.loadAccounts()
            _uiState.value = true
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("SplashViewModel", "ViewModel уничтожен, отменяем все задачи")
    }
}
