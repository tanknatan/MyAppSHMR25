package com.natan.shamilov.shmr25.app.data

import com.natan.shamilov.shmr25.BuildConfig
import com.natan.shamilov.shmr25.common.api.AppInfoProvider
import javax.inject.Inject

class AppInfoReposiroryImpl @Inject constructor() : AppInfoProvider {
    override val version = BuildConfig.VERSION_NAME
}