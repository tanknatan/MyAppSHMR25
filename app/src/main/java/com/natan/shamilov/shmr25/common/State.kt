package com.natan.shamilov.shmr25.common

sealed interface State {

    data object Loading : State

    data object Error : State

    data object Content : State
}
