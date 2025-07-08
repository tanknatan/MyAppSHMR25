package com.natan.shamilov.shmr25.app.network

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.app.data.api.NetworkStateReceiver
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel для управления сетевыми событиями на уровне приложения.
 * Ответственность: Обработка и распространение событий, связанных с состоянием
 * сетевого подключения, таких как отображение уведомлений об отсутствии соединения.
 */
class NetworkViewModel @Inject constructor(
    private val networkStateReceiver: NetworkStateReceiver
) : ViewModel() {

    private val _events = Channel<NetworkEvent>()
    val events = _events.receiveAsFlow()

    init {
        viewModelScope.launch {
            networkStateReceiver.isNetworkAvailable.collect { isAvailable ->
                if (!isAvailable) {
                    _events.send(NetworkEvent.ShowNoConnectionToast)
                }
            }
        }
    }
}
