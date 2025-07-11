package com.natan.shamilov.shmr25.account.presentation.screen.editAccount

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.account.domain.usecase.GetSelectedAccountUseCase
import com.natan.shamilov.shmr25.account.domain.usecase.LoadAccountListUseCase
import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.common.impl.domain.entity.Account
import com.natan.shamilov.shmr25.common.impl.domain.entity.CurrencyOption
import com.natan.shamilov.shmr25.common.impl.domain.entity.State
import com.natan.shamilov.shmr25.common.impl.domain.entity.currencyOptions
import com.natan.shamilov.shmr25.feature.account.domain.usecase.EditAccountUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EditAccountViewModel @Inject constructor(
    private val editAccountUseCase: EditAccountUseCase,
    private val getselectedAccountUseCase: GetSelectedAccountUseCase,
    private val loadAccountListUseCase: LoadAccountListUseCase,
    // private val networkStateReceiver: NetworkStateReceiver,
) : ViewModel() {

    private val _account = MutableStateFlow<Account?>(null)
    val account: StateFlow<Account?> = _account.asStateFlow()

    private val _uiState = MutableStateFlow<State>(State.Loading)
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    private val _accountName = MutableStateFlow("")
    val accountName: StateFlow<String> = _accountName.asStateFlow()

    private val _balance = MutableStateFlow("")
    val balance: StateFlow<String> = _balance.asStateFlow()

    private val _selectedCurrency = MutableStateFlow<CurrencyOption?>(null)
    val selectedCurrency: StateFlow<CurrencyOption?> = _selectedCurrency.asStateFlow()

    init {
        viewModelScope.launch {
//            networkStateReceiver.isNetworkAvailable.collect { isAvailable ->
//                _uiState.value = if (isAvailable) {
//                    State.Content
//                } else {
//                    State.Error
//                }
//            }
        }
    }

    fun loadAccount() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = State.Loading
            val foundAccount = getselectedAccountUseCase()
            _account.value = foundAccount
            if (foundAccount != null) {
                _accountName.value = foundAccount.name
                _balance.value = foundAccount.balance.toString()
                _selectedCurrency.value = currencyOptions.find { it.code == foundAccount.currency }
            }
            _uiState.value = State.Content
        }
    }

    fun editAccount(
        name: String,
        balance: String,
        currency: String,
        onSuccess: () -> Unit,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
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
                    withContext(Dispatchers.Main) {
                        onSuccess()
                    }
                }

                is Result.Error -> {
                    _uiState.value = State.Error
                    Log.e("AddAccountViewModel", "Ошибка создания аккаунта: ${result.exception.message}")
                }

                is Result.Loading -> {
                    _uiState.value = State.Loading
                }
            }
        }
    }

    fun onAccountNameChange(newName: String) {
        _accountName.value = newName
    }

    fun onBalanceChange(newBalance: String) {
        _balance.value = newBalance
    }

    fun onCurrencyChange(newCurrency: CurrencyOption?) {
        _selectedCurrency.value = newCurrency
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("EditAccountViewModel", "ViewModel уничтожен, отменяем все задачи")
    }
}
