package com.natan.shamilov.shmr25.common.impl.domain.repository

/**
 * Интерфейс репозитория для управления WorkManager задачами.
 * Предоставляет методы для мониторинга состояния синхронизации.
 */
interface WorkManagerRepository {

    suspend fun schedulePeriodicSync()

    fun triggerImmediateSync()
} 