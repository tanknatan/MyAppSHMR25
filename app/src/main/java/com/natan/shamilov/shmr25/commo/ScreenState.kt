package com.natan.shamilov.shmr25.commo

sealed interface State {

    data object Loading : State

    data object Error : State

    data object Content : State
}