package com.natan.shamilov.shmr25.account.impl.presentation.screen.accounts

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.account.impl.domain.usecase.GetAccountUseCase
import com.natan.shamilov.shmr25.account.impl.domain.usecase.GetIncomeExpenseMapsForLast31DaysUsecase
import com.natan.shamilov.shmr25.account.impl.domain.usecase.GetSelectedAccountUseCase
import com.natan.shamilov.shmr25.common.api.HapticProvider
import com.natan.shamilov.shmr25.common.impl.domain.entity.Account
import com.natan.shamilov.shmr25.common.impl.domain.entity.State
import com.natan.shamilov.shmr25.common.impl.presentation.BaseViewModel
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
    private val getAccountUseCase: GetAccountUseCase,
    private val getSelectedAccountUseCase: GetSelectedAccountUseCase,
    private val setSelectedAccountUseCase: SetSelectedAccountUseCase,
    private val getIncomeExpenseMapsForLast31DaysUsecase: GetIncomeExpenseMapsForLast31DaysUsecase,
    private val hapticProvider: HapticProvider,
) : BaseViewModel(hapticProvider) {

    private val _accounts = MutableStateFlow<List<Account>>(emptyList())
    val accounts: StateFlow<List<Account>> = _accounts.asStateFlow()

    private val _scheduleData = MutableStateFlow<Pair<Map<String, Double>, Map<String, Double>>?>(null)
    val scheduleData: StateFlow<Pair<Map<String, Double>, Map<String, Double>>?> = _scheduleData.asStateFlow()

    private val _selectedAccount = MutableStateFlow<Account?>(null)
    val selectedAccount: StateFlow<Account?> = _selectedAccount.asStateFlow()

    private val _uiState = MutableStateFlow<State>(State.Loading)
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    fun initialize() {
        viewModelScope.launch(Dispatchers.Default) {
            _uiState.value = State.Loading
            loadAccounts()
            loadScheduleData()
            _uiState.value = State.Content
            Log.d("AccountViewModel", "ViewModel инициализирован")
        }
    }

    fun selectAccount(account: Account) {
        viewModelScope.launch {
            setSelectedAccountUseCase(account.id)
            _selectedAccount.value = getSelectedAccountUseCase()
        }
    }

    private suspend fun loadScheduleData() {
        _scheduleData.value = getIncomeExpenseMapsForLast31DaysUsecase()
    }

    private suspend fun loadAccounts() {
        _accounts.value = getAccountUseCase()
        Log.d("loadtest2", _accounts.value.toString())
        _selectedAccount.value = getSelectedAccountUseCase()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("AccountViewModel", "ViewModel уничтожен, отменяем все задачи")
    }
}
