package com.natan.shamilov.shmr25.option.api

import com.natan.shamilov.shmr25.common.api.AppInfoProvider
import com.natan.shamilov.shmr25.common.api.HapticProvider
import com.natan.shamilov.shmr25.common.api.OptionsProvider
import com.natan.shamilov.shmr25.common.api.SyncPreferencesProvider
import com.natan.shamilov.shmr25.common.api.WorkManagerProvider

interface OptionsDependencies {
    val optionsProvider: OptionsProvider

    val workManagerProvider: WorkManagerProvider

    val syncPreferencesProvider: SyncPreferencesProvider

    val hapticManager: HapticProvider

    val appInfoProvider: AppInfoProvider
}