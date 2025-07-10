package com.natan.shamilov.shmr25.feature.account.presentation.screen.addAccount

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.common.network.NetworkStateReceiver
import com.natan.shamilov.shmr25.common.data.model.Result
import com.natan.shamilov.shmr25.common.domain.entity.Account
import com.natan.shamilov.shmr25.common.domain.entity.State
import com.natan.shamilov.shmr25.feature.account.domain.usecase.CreateAccountUseCase
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel для экрана добавления нового счета.
 * Ответственность: Управление бизнес-логикой и состоянием UI при создании нового счета,
 * включая валидацию данных, обработку сетевых запросов и отображение состояния загрузки.
 */
class AddAccountViewModel @Inject constructor(
    private val createAccountUseCase: CreateAccountUseCase,
    private val networkStateReceiver: NetworkStateReceiver
) : ViewModel() {

    private val _accounts = MutableStateFlow<List<Account>>(emptyList())
    val accounts: StateFlow<List<Account>> = _accounts.asStateFlow()

    private val _uiState = MutableStateFlow<State>(State.Loading)
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            networkStateReceiver.isNetworkAvailable.collect { isAvailable ->
                _uiState.value = if (isAvailable) {
                    State.Content
                } else {
                    State.Error
                }
            }
        }
    }

    fun createAccount(
        name: String,
        balance: String,
        currency: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _uiState.value = State.Loading

                when (val result = createAccountUseCase(name, balance, currency)) {
                    is Result.Success -> {
                        _uiState.value = State.Content
                        onSuccess()
                    }
                    is Result.Error -> {
                        _uiState.value = State.Error
                        Log.e("AddAccountViewModel", "Ошибка создания аккаунта: ${result.exception.message}")
                    }
                    is Result.Loading -> {
                        _uiState.value = State.Loading
                    }
                }
            } catch (e: Exception) {
                if (e is kotlinx.coroutines.CancellationException) throw e
                _uiState.value = State.Error
                Log.e("AddAccountViewModel", "Неожиданная ошибка при создании аккаунта", e)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("AddAccountViewModel", "ViewModel уничтожен, отменяем все задачи")
    }
}
