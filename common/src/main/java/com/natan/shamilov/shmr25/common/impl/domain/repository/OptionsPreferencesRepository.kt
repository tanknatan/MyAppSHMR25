package com.natan.shamilov.shmr25.common.impl.domain.repository

import com.natan.shamilov.shmr25.common.impl.domain.entity.HapticType
import com.natan.shamilov.shmr25.common.impl.presentation.ui.theme.MainColorType
import kotlinx.coroutines.flow.StateFlow

interface OptionsPreferencesRepository {

    suspend fun getThemeMode(): Boolean

    suspend fun setThemeMode(isDarkMode: Boolean)

    suspend fun getThemeModeFlow(): StateFlow<Boolean>

    suspend fun setHapticEnabledState(isHapticEnabled: Boolean)

    suspend fun getHapticEnabledState(): Boolean

    suspend fun setHapticType(type: HapticType)

    suspend fun getHapticType(): HapticType

    suspend fun setLanguage(language: String)

    suspend fun getLanguage(): String

    suspend fun getLocaleFlow(): StateFlow<String>

    suspend fun getMainThemeColor(): MainColorType

    suspend fun setMainThemeColor(color: MainColorType)

    suspend fun getMainThemeColorFlow(): StateFlow<MainColorType>

    suspend fun getPinCode(): Int

    suspend fun setPinCode(pin: Int)
}