package com.natan.shamilov.shmr25.splash.impl

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.common.api.OptionsProvider
import com.natan.shamilov.shmr25.common.impl.data.model.Result
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
    private val categoriesStartupLoader: CategoriesStartupLoader,
    private val  optionsProvider: OptionsProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow(false)
    val uiState: StateFlow<Boolean> = _uiState.asStateFlow()

    private val _isPinCodeSet = MutableStateFlow(false)
    val isPinCodeSet: StateFlow<Boolean> = _isPinCodeSet.asStateFlow()

    init {
        Log.d("SplashViewModel", "Инициализация SplashViewModel")
        loadDataInBackground()
    }

    private fun loadDataInBackground() {
        viewModelScope.launch(Dispatchers.IO) {
            _isPinCodeSet.value = optionsProvider.getPinCode() != 0
            when (val result = accountStartupLoader.loadAccounts()) {
                is Result.Error -> Log.e("SplashViewModel", "Ошибка загрузки счетов: ${result.exception.message}")
                Result.Loading -> Log.d("SplashViewModel", "Загрузка счетов в процессе")
                is Result.Success<*> -> {
                    Log.d("SplashViewModel", "Счета загружены")
                    categoriesStartupLoader.load()
                }
            }

            _uiState.value = true
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("SplashViewModel", "ViewModel уничтожен, отменяем все задачи")
    }
}
