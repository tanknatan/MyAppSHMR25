package com.natan.shamilov.shmr25.presentation.feature.account.presentation.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.common.State
import com.natan.shamilov.shmr25.data.api.NetworkStateReceiver
import com.natan.shamilov.shmr25.domain.entity.Account
import com.natan.shamilov.shmr25.domain.usecase.CheckAccAndTransactionDataLoadingUseCase
import com.natan.shamilov.shmr25.domain.usecase.CreateAccountUseCase
import com.natan.shamilov.shmr25.domain.usecase.GetAccountUseCase
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
class AccountViewModel @Inject constructor(
    private val getAccountUseCase: GetAccountUseCase,
    private val createAccountUseCase: CreateAccountUseCase,
    private val checkAccAndTransactionDataLoadingUseCase: CheckAccAndTransactionDataLoadingUseCase,
    private val networkStateReceiver: NetworkStateReceiver
) : ViewModel() {

    private val _account: MutableStateFlow<List<Account>> = MutableStateFlow(emptyList())
    val account: StateFlow<List<Account>>
        get() = _account

    private val _selectedAccount: MutableStateFlow<Account?> = MutableStateFlow(null)
    val selectedAccount: StateFlow<Account?>
        get() = _selectedAccount

    private val _uiState = MutableStateFlow<State>(State.Loading)
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    private var dataLoadingJob: Job? = null
    private var networkJob: Job? = null

    fun selectAccount(account: Account) {
        _selectedAccount.value = account
    }

    init {
        // Только подписываемся на сеть, но не загружаем данные сразу
        networkJob = viewModelScope.launch {
            networkStateReceiver.isNetworkAvailable.collect { isAvailable ->
                if (isAvailable && _uiState.value == State.Error) {
                    getAccount()
                }
            }
        }
    }

    fun initialize() {
        // Загружаем данные только при первом показе экрана
        getAccount()
    }

    private fun getAccount() {
        // Отменяем предыдущую задачу если она существует
        dataLoadingJob?.cancel()
        
        dataLoadingJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                checkAccAndTransactionDataLoadingUseCase().collect {
                    withContext(Dispatchers.Main) {
                        _uiState.value = it
                    }
                    when (it) {
                        State.Content -> {
                            val accounts = getAccountUseCase()
                            withContext(Dispatchers.Main) {
                                _account.value = accounts
                                _selectedAccount.value = accounts.firstOrNull()
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
            } catch (e: Exception) {
                if (e is kotlinx.coroutines.CancellationException) {
                    Log.d("AccountViewModel", "Загрузка аккаунтов отменена")
                } else {
                    Log.e("AccountViewModel", "Ошибка при загрузке аккаунтов: ${e.message}")
                }
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
