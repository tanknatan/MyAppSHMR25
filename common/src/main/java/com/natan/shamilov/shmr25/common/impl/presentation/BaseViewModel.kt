package com.natan.shamilov.shmr25.common.impl.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.common.api.HapticProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

open class BaseViewModel @Inject constructor(
    private val hapticProvider: HapticProvider,
) : ViewModel() {

    fun vibrate() {
        viewModelScope.launch {
            hapticProvider.triggerHaptic()
        }
    }
}