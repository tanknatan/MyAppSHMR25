package com.natan.shamilov.shmr25.common.impl.domain.repository

import kotlinx.coroutines.flow.StateFlow

interface NetworkRepository {
    fun startNetworkMonitoring()
    fun stopNetworkMonitoring()
    fun getNetworkStatus(): StateFlow<Boolean>
}