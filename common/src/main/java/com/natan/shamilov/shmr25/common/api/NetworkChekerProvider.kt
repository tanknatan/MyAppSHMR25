package com.natan.shamilov.shmr25.common.api

import kotlinx.coroutines.flow.StateFlow

interface NetworkChekerProvider {
    fun startNetworkMonitoring()
    fun stopNetworkMonitoring()
    fun getNetworkStatus(): StateFlow<Boolean>
}