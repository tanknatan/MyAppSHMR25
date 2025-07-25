package com.natan.shamilov.shmr25.common.impl.data.haptic

import android.Manifest
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.annotation.RequiresPermission
import com.natan.shamilov.shmr25.common.api.HapticProvider
import com.natan.shamilov.shmr25.common.api.OptionsProvider
import com.natan.shamilov.shmr25.common.impl.domain.entity.HapticType
import javax.inject.Inject

class HapticManagerImpl @Inject constructor(
    private val context: Context,
    private val optionsPreferencesRepository: OptionsProvider,
) : HapticManager , HapticProvider {

    private val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    @RequiresPermission(Manifest.permission.VIBRATE)
    private fun lightHaptic() {
        vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
    }

    @RequiresPermission(Manifest.permission.VIBRATE)
    private fun mediumHaptic() {
        vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
    }

    @RequiresPermission(Manifest.permission.VIBRATE)
    private fun longHaptic() {
        vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
    }

    override suspend fun triggerHaptic() {
        if (optionsPreferencesRepository.getHapticEnabledState() == true) {
            when (optionsPreferencesRepository.getHapticType()) {
                HapticType.SHORT -> {
                    lightHaptic()
                }

                HapticType.MEDIUM -> {
                    mediumHaptic()
                }

                HapticType.LONG -> {
                    longHaptic()
                }
            }
        }
    }
}