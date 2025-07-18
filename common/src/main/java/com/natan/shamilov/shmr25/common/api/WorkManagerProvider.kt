package com.natan.shamilov.shmr25.common.api

interface WorkManagerProvider {
    fun triggerImmediateSync()
    fun schedulePeriodicSync()
}