package com.natan.shamilov.shmr25.app.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.app.network.NetworkEvent
import com.natan.shamilov.shmr25.common.api.NetworkChekerProvider
import com.natan.shamilov.shmr25.common.api.WorkManagerProvider
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel для управления сетевыми событиями на уровне приложения.
 * Ответственность: Обработка и распространение событий, связанных с состоянием
 * сетевого подключения, таких как отображение уведомлений об отсутствии соединения.
 */
class NetworkViewModel @Inject constructor(
    private val networkChekerProvider: NetworkChekerProvider,
    private val workManagerProvider: WorkManagerProvider,
) : ViewModel() {

    private val _events = MutableSharedFlow<NetworkEvent>()
    val events: SharedFlow<NetworkEvent> = _events.asSharedFlow()

    init {
        networkChekerProvider.startNetworkMonitoring()
        viewModelScope.launch {
            networkChekerProvider.getNetworkStatus().collect { isAvailable ->
                if (!isAvailable) {
                    _events.emit(NetworkEvent.ShowNoConnectionToast)
                } else {
                    workManagerProvider.triggerImmediateSync()
                }
            }
        }
    }
}
