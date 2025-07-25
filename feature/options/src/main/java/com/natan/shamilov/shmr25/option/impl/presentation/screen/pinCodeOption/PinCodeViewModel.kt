package com.natan.shamilov.shmr25.option.impl.presentation.screen.pinCodeOption

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.common.api.OptionsProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PinCodeViewModel @Inject constructor(
    private val optionsProvider: OptionsProvider,
) : ViewModel() {

    fun setPinCode(pin: String, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            optionsProvider.setPinCode(pin.hashCode())
            withContext(Dispatchers.Main) {
                onSuccess()
            }
        }
    }
}