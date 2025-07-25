package com.natan.shamilov.shmr25.login.api

import com.natan.shamilov.shmr25.common.api.HapticProvider
import com.natan.shamilov.shmr25.common.api.OptionsProvider

interface LoginDependencies {
    val optionsProvider: OptionsProvider
    val hapticProvider : HapticProvider
}