package com.natan.shamilov.shmr25.option.impl.presentation.screen.languageOption

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.common.api.OptionsProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChangeLocaleViewModel @Inject constructor(
    private val optionsProvider: OptionsProvider,
) : ViewModel() {

    private val _locale = MutableStateFlow("ru")
    val locale = _locale.asStateFlow()

    init {
        observeLocale()
    }

    private fun observeLocale() {
        viewModelScope.launch(Dispatchers.IO) {
            optionsProvider.getLocaleFlow().collect { locale ->
                Log.d("ChangeLocaleTest", "Locale changed to: $locale")
                _locale.value = locale
            }
        }
    }

    fun setLocale(locale: String) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("ChangeLocaleTest", "Try to change locale to: $locale")
            optionsProvider.setLanguage(locale)
        }
    }
}