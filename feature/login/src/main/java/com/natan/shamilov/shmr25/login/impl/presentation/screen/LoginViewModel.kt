package com.natan.shamilov.shmr25.login.impl.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.common.api.OptionsProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val optionsProvider: OptionsProvider,
) : ViewModel() {

    private var pin = 0

    init {
        getPinCode()
    }

    private fun getPinCode() {
        viewModelScope.launch(Dispatchers.IO) {
            pin = optionsProvider.getPinCode()
        }
    }

    fun checkPassword(password: String): Boolean {
        return password.hashCode() == pin
    }
}