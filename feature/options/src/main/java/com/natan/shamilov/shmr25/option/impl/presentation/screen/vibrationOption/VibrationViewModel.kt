package com.natan.shamilov.shmr25.option.impl.presentation.screen.vibrationOption

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.common.api.HapticProvider
import com.natan.shamilov.shmr25.common.api.OptionsProvider
import com.natan.shamilov.shmr25.common.impl.domain.entity.HapticType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class VibrationViewModel @Inject constructor(
    private val optionsProvider: OptionsProvider,
    private val hapticProvider: HapticProvider,

    ) : ViewModel() {

    private val _hapticType = MutableStateFlow(HapticType.getDefault())
    val hapticType = _hapticType.asStateFlow()

    private val _hapticEnabledState = MutableStateFlow(false)
    val hapticEnabledState = _hapticEnabledState.asStateFlow()

    init {
        getHapticEnabledState()
        getHapticType()
    }

    fun setHapticType(type: HapticType) {
        viewModelScope.launch(Dispatchers.IO) {
            optionsProvider.setHapticType(type)
            _hapticType.value = type
            hapticProvider.triggerHaptic()
        }
    }

    fun setHapticEnabledState(state: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            optionsProvider.setHapticEnabledState(state)
            _hapticEnabledState.value = state
        }
    }

    private fun getHapticType() {
        viewModelScope.launch(Dispatchers.IO) {
            _hapticType.value = optionsProvider.getHapticType()
        }
    }

    private fun getHapticEnabledState() {
        viewModelScope.launch(Dispatchers.IO) {
            _hapticEnabledState.value = optionsProvider.getHapticEnabledState()
        }
    }
}