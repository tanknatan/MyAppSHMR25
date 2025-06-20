package com.natan.shamilov.shmr25.presentation.feature.account.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.commo.State
import com.natan.shamilov.shmr25.domain.entity.Account
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
) : ViewModel() {

    private val _account: MutableStateFlow<List<Account>> = MutableStateFlow(emptyList())
    val account: StateFlow<List<Account>>
        get() = _account

    private val _selectedAccount: MutableStateFlow<Account?> = MutableStateFlow(null)
    val selectedAccount: StateFlow<Account?>
        get() = _selectedAccount

    private val _uiState = MutableStateFlow<State>(State.Loading)

    val uiState: StateFlow<State> = _uiState.asStateFlow()


    fun selectAccount(account: Account) {
        _selectedAccount.value = account
    }

    init {
        getAccount()
    }


    private fun getAccount() {
        viewModelScope.launch {
            _account.value = getAccountUseCase()
            _selectedAccount.value = _account.value.firstOrNull()
            _uiState.value = State.Content
        }
    }
}