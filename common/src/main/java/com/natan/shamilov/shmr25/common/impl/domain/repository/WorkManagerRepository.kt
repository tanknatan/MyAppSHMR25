package com.natan.shamilov.shmr25.common.impl.domain.repository

/**
 * Интерфейс репозитория для управления WorkManager задачами.
 * Предоставляет методы для мониторинга состояния синхронизации.
 */
interface WorkManagerRepository {

    fun schedulePeriodicSync()

    fun triggerImmediateSync()
} 