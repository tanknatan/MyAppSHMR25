package com.natan.shamilov.shmr25.feature.account.presentation.screen.accounts

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.common.State
import com.natan.shamilov.shmr25.app.data.api.NetworkStateReceiver
import com.natan.shamilov.shmr25.app.data.api.Result
import com.natan.shamilov.shmr25.feature.account.domain.entity.Account
import com.natan.shamilov.shmr25.feature.account.domain.usecase.GetAccountUseCase
import com.natan.shamilov.shmr25.feature.account.domain.usecase.LoadAccountsListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.Job

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val getAccountUseCase: GetAccountUseCase,
    private val loadAccountsListUseCase: LoadAccountsListUseCase,
    private val networkStateReceiver: NetworkStateReceiver
) : ViewModel() {

    private val _accounts = MutableStateFlow<List<Account>>(emptyList())
    val accounts: StateFlow<List<Account>> = _accounts.asStateFlow()

    private val _selectedAccount = MutableStateFlow<Account?>(null)
    val selectedAccount: StateFlow<Account?> = _selectedAccount.asStateFlow()

    private val _uiState = MutableStateFlow<State>(State.Loading)
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    private var dataLoadingJob: Job? = null
    private var networkJob: Job? = null

    init {
        networkJob = viewModelScope.launch {
            networkStateReceiver.isNetworkAvailable.collect { isAvailable ->
                if (isAvailable && _uiState.value == State.Error) {
                    loadAccounts()
                }
            }
        }
    }

    fun initialize() {
        loadAccounts()
    }

    fun selectAccount(account: Account) {
        _selectedAccount.value = account
    }

    private fun loadAccounts() {
        dataLoadingJob?.cancel()
        
        dataLoadingJob = viewModelScope.launch {
            try {
                _uiState.value = State.Loading
                
                // Сначала загружаем данные с сервера
                when (val result = loadAccountsListUseCase()) {
                    is Result.Success -> {
                        // После успешной загрузки получаем список из локальной БД
                        val accounts = getAccountUseCase()
                        if (accounts.isEmpty()) {
                            _uiState.value = State.Error
                            Log.w("AccountViewModel", "Список аккаунтов пуст после успешной загрузки")
                        } else {
                            _accounts.value = accounts
                            _selectedAccount.value = accounts.firstOrNull()
                            _uiState.value = State.Content
                        }
                    }
                    is Result.Error -> {
                        // Пробуем получить данные из локальной БД даже при ошибке загрузки
                        val accounts = getAccountUseCase()
                        if (accounts.isNotEmpty()) {
                            _accounts.value = accounts
                            _selectedAccount.value = accounts.firstOrNull()
                            _uiState.value = State.Content
                            Log.w("AccountViewModel", "Используем кэшированные данные: ${result.exception.message}")
                        } else {
                            _uiState.value = State.Error
                            Log.e("AccountViewModel", "Ошибка загрузки аккаунтов: ${result.exception.message}")
                        }
                    }
                    is Result.Loading -> {
                        _uiState.value = State.Loading
                    }
                }
            } catch (e: Exception) {
                if (e is kotlinx.coroutines.CancellationException) throw e
                _uiState.value = State.Error
                Log.e("AccountViewModel", "Неожиданная ошибка при загрузке аккаунтов", e)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("AccountViewModel", "ViewModel уничтожен, отменяем все задачи")
        dataLoadingJob?.cancel()
        networkJob?.cancel()
    }
}
