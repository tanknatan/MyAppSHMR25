package com.natan.shamilov.common.network

import kotlinx.coroutines.flow.Flow

interface NetworkStateProvider {
    fun isNetworkAvailable(): Boolean
    fun observeNetworkState(): Flow<Boolean>
} 