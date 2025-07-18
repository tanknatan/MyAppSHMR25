package com.natan.shamilov.shmr25.common.impl.domain.repository

interface SyncPreferencesRepository {
    suspend fun saveLastSyncInfo(timestamp: Long, status: String)
    suspend fun getLastSyncInfo(): Pair<Long?, String?>

    companion object {
        const val SYNC_SUCCESS = "Успешно синхронизованно"
        const val SYNC_ERROR = "Ошибка при синхронизации"
    }
}