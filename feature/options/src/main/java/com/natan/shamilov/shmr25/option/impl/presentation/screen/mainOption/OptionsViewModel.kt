package com.natan.shamilov.shmr25.option.impl.presentation.screen.mainOption

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.common.api.OptionsProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class OptionsViewModel @Inject constructor(
    private val optionsProvider: OptionsProvider,
) : ViewModel() {
    private var _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    init {
        getThemeMode()
    }

    fun getThemeMode() {
        viewModelScope.launch(Dispatchers.IO) {
            _isDarkTheme.value = optionsProvider.getThemeMode()
        }
    }

    fun setThemeMode(newThemeMode: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _isDarkTheme.value = newThemeMode
            optionsProvider.setThemeMode(newThemeMode)
        }
    }
}