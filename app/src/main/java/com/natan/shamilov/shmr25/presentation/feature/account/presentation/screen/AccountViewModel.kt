package com.natan.shamilov.shmr25.presentation.feature.account.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.commo.State
import com.natan.shamilov.shmr25.data.api.Result
import com.natan.shamilov.shmr25.domain.entity.Account
import com.natan.shamilov.shmr25.domain.usecase.CheckAccAndTransactionDataLoadingUseCase
import com.natan.shamilov.shmr25.domain.usecase.CreateAccountUseCase
import com.natan.shamilov.shmr25.domain.usecase.GetAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val getAccountUseCase: GetAccountUseCase,
    private val createAccountUseCase: CreateAccountUseCase,
    private val checkAccAndTransactionDataLoadingUseCase: CheckAccAndTransactionDataLoadingUseCase,
) : ViewModel() {

    private val _account: MutableStateFlow<List<Account>> = MutableStateFlow(emptyList())
    val account: StateFlow<List<Account>>
        get() = _account

    private val _selectedAccount: MutableStateFlow<Account?> = MutableStateFlow(null)
    val selectedAccount: StateFlow<Account?>
        get() = _selectedAccount

    private val _uiState = MutableStateFlow<State>(State.Loading)
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    private val _createAccountComplete = MutableStateFlow(false)
    val createAccountComplete: StateFlow<Boolean> = _createAccountComplete


    fun selectAccount(account: Account) {
        _selectedAccount.value = account
    }

    init {
        getAccount()
    }

    fun resetCreateAccountComplete() {
        _createAccountComplete.value = false
    }


    private fun getAccount() {
        viewModelScope.launch {
            checkAccAndTransactionDataLoadingUseCase().collect {
                _uiState.value = it
                when (it) {
                    State.Content -> {
                        _account.value = getAccountUseCase()
                        _selectedAccount.value = _account.value.firstOrNull()
                    }

                    else -> Unit
                }
            }
        }
    }

    fun createAccount(name: String, balance: String, currency: String) {
        _uiState.value = State.Loading
        viewModelScope.launch {
            when (createAccountUseCase(
                name = name,
                balance = balance,
                currency = currency
            )) {
                is Result.Error -> {
                    _uiState.value = State.Error
                }

                Result.Loading -> State.Loading
                is Result.Success<*> -> {
                    _createAccountComplete.value = true
                    _uiState.value = State.Content
                }
            }
            getAccount()

        }
    }
}