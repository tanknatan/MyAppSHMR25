package com.natan.shamilov.shmr25.common.impl.data.repository

import android.content.Context
import android.util.Log
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.natan.shamilov.shmr25.common.api.OptionsProvider
import com.natan.shamilov.shmr25.common.impl.domain.entity.HapticType
import com.natan.shamilov.shmr25.common.impl.domain.repository.OptionsPreferencesRepository
import com.natan.shamilov.shmr25.common.impl.presentation.ui.theme.MainColorType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class OptionsPreferencesRepositoryImpl @Inject constructor(
    private val context: Context,
) : OptionsPreferencesRepository, OptionsProvider {

    val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    val encryptedSharedPreferences = EncryptedSharedPreferences.create(
        ENCRYPTED_PREF,
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private val sharedPreferences = context.getSharedPreferences(
        OPTIONS_PREF,
        Context.MODE_PRIVATE
    )

    private val isDarkTheme = MutableStateFlow(false)

    private val locale = MutableStateFlow("ru")

    private val mainColor = MutableStateFlow(MainColorType.getDefault())

    override suspend fun getThemeMode(): Boolean {
        isDarkTheme.value = sharedPreferences.getBoolean(KEY_THEME_MODE, false)
        return isDarkTheme.value
    }

    override suspend fun setThemeMode(isDarkMode: Boolean) {
        sharedPreferences.edit {
            putBoolean(KEY_THEME_MODE, isDarkMode)
        }
        isDarkTheme.value = isDarkMode
    }

    override suspend fun getThemeModeFlow(): StateFlow<Boolean> {
        getThemeMode()
        return isDarkTheme
    }

    override suspend fun setHapticEnabledState(isHapticEnabled: Boolean) {
        sharedPreferences.edit {
            putBoolean(KEY_HAPTIC_ENABLED_STATE, isHapticEnabled)
        }
    }

    override suspend fun getHapticEnabledState(): Boolean {
        return sharedPreferences.getBoolean(KEY_HAPTIC_ENABLED_STATE, true)
    }

    override suspend fun setHapticType(type: HapticType) {
        sharedPreferences.edit {
            putString(KEY_HAPTIC_TYPE, type.value)
        }
    }

    override suspend fun getHapticType(): HapticType {
        val savedValue = sharedPreferences.getString(KEY_HAPTIC_TYPE, HapticType.getDefault().value)
        return if (savedValue != null) {
            HapticType.fromValue(savedValue)
        } else {
            HapticType.getDefault()
        }
    }

    override suspend fun setLanguage(language: String) {
        sharedPreferences.edit {
            putString(KEY_LOCALE, language)
        }
        locale.value = language
        Log.d("ChangeLocaleTest", "Locale changed in repository to: ${locale.value}")
    }

    override suspend fun getLanguage(): String {
        locale.value = sharedPreferences.getString(KEY_LOCALE, "ru") ?: "ru"
        return locale.value
    }

    override suspend fun getLocaleFlow(): StateFlow<String> {
        getLanguage()
        return locale
    }

    override suspend fun getMainThemeColor(): MainColorType {
        val savedValue =
            sharedPreferences.getString(KEY_MAIN_THEME_COLOR, MainColorType.getDefault().color)
        mainColor.value = if (savedValue != null) {
            MainColorType.fromValue(savedValue)
        } else {
            MainColorType.getDefault()
        }
        return mainColor.value
    }

    override suspend fun setMainThemeColor(color: MainColorType) {
        sharedPreferences.edit {
            putString(KEY_MAIN_THEME_COLOR, color.color)
        }
        mainColor.value = color
    }

    override suspend fun getMainThemeColorFlow(): StateFlow<MainColorType> {
        getMainThemeColor()
        return mainColor
    }

    override suspend fun getPinCode(): Int {
        return encryptedSharedPreferences.getInt(KEY_PIN_CODE, 0)
    }

    override suspend fun setPinCode(pin: Int) {
        encryptedSharedPreferences.edit {
            putInt(KEY_PIN_CODE, pin)
        }
    }

    companion object {
        private const val OPTIONS_PREF = "options_pref"
        private const val ENCRYPTED_PREF = "encrypted_pref"
        private const val KEY_THEME_MODE = "theme_mode"
        private const val KEY_HAPTIC_ENABLED_STATE = "haptic_state"
        private const val KEY_HAPTIC_TYPE = "haptic_type"
        private const val KEY_LOCALE = "locale"
        private const val KEY_MAIN_THEME_COLOR = "main_color"
        private const val KEY_PIN_CODE = "pin_code"
    }
}