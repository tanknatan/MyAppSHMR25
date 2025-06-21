package com.natan.shamilov.shmr25.presentation

sealed class NetworkEvent {
    object ShowNoConnectionToast : NetworkEvent()
}