package com.natan.shamilov.shmr25.common.api

interface SyncPreferencesProvider {

    suspend fun saveLastSyncInfo(timestamp: Long, status: String)

    suspend fun getLastSyncInfo(): Pair<Long?, String?>
}
