package com.natan.shamilov.shmr25.presentation.screens.account

import androidx.lifecycle.ViewModel
import com.natan.shamilov.shmr25.domain.entity.Account
import com.natan.shamilov.shmr25.domain.usecase.GetAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val getAccountUseCase: GetAccountUseCase,
) : ViewModel() {

    private val _account: MutableStateFlow<Account?> = MutableStateFlow(null)
    val account: StateFlow<Account?>
        get() = _account

    init {
        getAccount()
    }


    private fun getAccount() {
        _account.value = getAccountUseCase()

    }
}