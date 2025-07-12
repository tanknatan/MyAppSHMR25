package com.natan.shamilov.shmr25.feature.account.presentation.screen.accounts

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.account.impl.domain.usecase.GetSelectedAccountUseCase
import com.natan.shamilov.shmr25.common.impl.domain.entity.Account
import com.natan.shamilov.shmr25.common.impl.domain.entity.State
import com.natan.shamilov.shmr25.feature.account.domain.usecase.GetAccountUseCase
import com.natan.shamilov.shmr25.feature.account.domain.usecase.SetSelectedAccountUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel для экрана счетов.
 * Ответственность: Управление данными и состоянием UI для отображения списка счетов,
 * включая загрузку счетов, выбор активного счета, обработку сетевых ошибок
 * и обновление данных при изменении состояния сети.
 */
class AccountViewModel @Inject constructor(
    // private val networkStateReceiver: NetworkStateReceiver,
    private val getAccountUseCase: GetAccountUseCase,
    private val getSelectedAccountUseCase: GetSelectedAccountUseCase,
    private val setSelectedAccountUseCase: SetSelectedAccountUseCase,
) : ViewModel() {

    private val _accounts = MutableStateFlow<List<Account>>(emptyList())
    val accounts: StateFlow<List<Account>> = _accounts.asStateFlow()

    private val _selectedAccount = MutableStateFlow<Account?>(null)
    val selectedAccount: StateFlow<Account?> = _selectedAccount.asStateFlow()

    private val _uiState = MutableStateFlow<State>(State.Loading)
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    init {
//        viewModelScope.launch {
////            networkStateReceiver.isNetworkAvailable.collect { isAvailable ->
////                if (isAvailable && _uiState.value == State.Error) {
////                    loadAccounts()
////                }
////            }
//        }
        Log.d("AccountViewModel", "ViewModel инициализирован")
    }

    fun initialize() {
        loadAccounts()
        Log.d("AccountViewModel", "ViewModel инициализирован")
    }

    fun selectAccount(account: Account) {
        viewModelScope.launch {
            setSelectedAccountUseCase(account.id)
            _selectedAccount.value = getSelectedAccountUseCase()
        }
    }

    private fun loadAccounts() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = State.Loading
            _accounts.value = getAccountUseCase()
            _selectedAccount.value = getSelectedAccountUseCase()
            _uiState.value = State.Content
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("AccountViewModel", "ViewModel уничтожен, отменяем все задачи")
    }
}
