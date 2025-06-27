package com.natan.shamilov.shmr25.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.data.api.NetworkStateReceiver
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
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
