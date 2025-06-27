package com.natan.shamilov.shmr25.app.network

sealed class NetworkEvent {
    object ShowNoConnectionToast : NetworkEvent()
}
