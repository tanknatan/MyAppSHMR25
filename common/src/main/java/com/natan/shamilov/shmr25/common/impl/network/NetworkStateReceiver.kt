package com.natan.shamilov.shmr25.common.impl.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Отслеживает состояние сетевого подключения устройства.
 * Ответственность: Мониторинг доступности сети и оповещение подписчиков об изменениях
 * состояния подключения через StateFlow.
 */
@Singleton
class NetworkStateReceiver @Inject constructor(
    context: Context
) {
    private val connectivityManager: ConnectivityManager? =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager

    private val _isNetworkAvailable = MutableStateFlow(getCurrentNetworkState())
    val isNetworkAvailable: StateFlow<Boolean> = _isNetworkAvailable.asStateFlow()

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            _isNetworkAvailable.value = true
        }

        override fun onLost(network: Network) {
            _isNetworkAvailable.value = false
        }

        override fun onCapabilitiesChanged(network: Network, capabilities: NetworkCapabilities) {
            _isNetworkAvailable.value =
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                        capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        }
    }

    private fun getCurrentNetworkState(): Boolean {
        return try {
            val manager = connectivityManager ?: return false
            val activeNetwork = manager.activeNetwork ?: return false
            val capabilities = manager.getNetworkCapabilities(activeNetwork) ?: return false

            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                    capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        } catch (e: Exception) {
            false
        }
    }

    fun register() {
        try {
            // Обновляем состояние перед регистрацией
            _isNetworkAvailable.value = getCurrentNetworkState()
            connectivityManager?.registerDefaultNetworkCallback(networkCallback)
        } catch (e: Exception) {
            // Если не удается зарегистрировать callback, просто игнорируем
        }
    }

    fun unregister() {
        try {
            connectivityManager?.unregisterNetworkCallback(networkCallback)
        } catch (e: Exception) {
            // Если не удается отменить регистрацию, просто игнорируем
        }
    }
}
