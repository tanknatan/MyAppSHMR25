package com.natan.shamilov.shmr25.feature.account.presentation.screen.editAccount

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.app.data.api.NetworkStateReceiver
import com.natan.shamilov.shmr25.app.data.api.Result
import com.natan.shamilov.shmr25.common.State
import com.natan.shamilov.shmr25.feature.account.domain.entity.Account
import com.natan.shamilov.shmr25.feature.account.domain.usecase.EditAccountUseCase
import com.natan.shamilov.shmr25.feature.account.domain.usecase.GetAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditAccountViewModel @Inject constructor(
    private val editAccountUseCase: EditAccountUseCase,
    private val getAccountUseCase: GetAccountUseCase,
    private val networkStateReceiver: NetworkStateReceiver,
) : ViewModel() {

    private val _account = MutableStateFlow<Account?>(null)
    val account: StateFlow<Account?> = _account.asStateFlow()

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

    suspend fun loadAccount(inputAccountId: String) {
        val accountId = inputAccountId.toInt()
        val accounts = getAccountUseCase()
        val foundAccount = accounts.find { it.id == accountId }!!
        _account.value = foundAccount
    }

    fun editAccount(
        name: String,
        balance: String,
        currency: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                _uiState.value = State.Loading

                when (
                    val result = editAccountUseCase(
                        accountId = _account.value!!.id,
                        name = name,
                        balance = balance,
                        currency = currency,
                    )
                ) {
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
        Log.d("EditAccountViewModel", "ViewModel уничтожен, отменяем все задачи")
    }
}
