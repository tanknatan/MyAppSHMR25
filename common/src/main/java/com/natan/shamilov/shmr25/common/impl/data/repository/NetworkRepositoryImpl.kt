package com.natan.shamilov.shmr25.common.impl.data.repository

import com.natan.shamilov.shmr25.common.api.NetworkChekerProvider
import com.natan.shamilov.shmr25.common.impl.domain.repository.NetworkRepository
import com.natan.shamilov.shmr25.common.impl.network.NetworkStateReceiver
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val networkStateReceiver: NetworkStateReceiver,
) : NetworkRepository, NetworkChekerProvider {
    override fun startNetworkMonitoring() {
        networkStateReceiver.register()
    }

    override fun stopNetworkMonitoring() {
        networkStateReceiver.unregister()
    }

    override fun getNetworkStatus(): StateFlow<Boolean> {
        return networkStateReceiver.isNetworkAvailable
    }
}