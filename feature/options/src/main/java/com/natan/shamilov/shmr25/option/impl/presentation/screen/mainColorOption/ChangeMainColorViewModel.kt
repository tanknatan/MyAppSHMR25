package com.natan.shamilov.shmr25.option.impl.presentation.screen.mainColorOption

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.common.api.OptionsProvider
import com.natan.shamilov.shmr25.common.impl.presentation.ui.theme.MainColorType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChangeMainColorViewModel @Inject constructor(
    private val optionsProvider: OptionsProvider,
) : ViewModel() {

    init {
        observeMainColor()
    }

    private val _mainColor = MutableStateFlow(MainColorType.getDefault())
    val mainColor = _mainColor.asStateFlow()

    private fun observeMainColor() {
        viewModelScope.launch(Dispatchers.IO) {
            optionsProvider.getMainThemeColorFlow().collect { mainColor ->
                _mainColor.value = mainColor
            }
        }
    }

    fun changeMainColor(color: MainColorType) {
        viewModelScope.launch(Dispatchers.IO) {
            optionsProvider.setMainThemeColor(color)
        }
    }
}
