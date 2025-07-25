package com.natan.shamilov.shmr25.common.impl.data.repository

import android.content.Context
import androidx.core.content.edit
import com.natan.shamilov.shmr25.common.api.SyncPreferencesProvider
import com.natan.shamilov.shmr25.common.impl.domain.repository.SyncPreferencesRepository
import javax.inject.Inject

class SyncPreferencesRepositoryImpl @Inject constructor(
    private val context: Context
): SyncPreferencesRepository, SyncPreferencesProvider {

    private val sharedPreferences = context.getSharedPreferences(
        SYNC_PREF,
        Context.MODE_PRIVATE
    )

    private suspend fun saveLastSyncTime(timestamp: Long) {
        sharedPreferences.edit {
            putLong(KEY_LAST_SYNC_TIME, timestamp)
        }
    }

    private suspend fun getLastSyncTime(): Long? {
        val timestamp = sharedPreferences.getLong(KEY_LAST_SYNC_TIME, -1L)
        return if (timestamp == -1L) null else timestamp
    }

    private suspend fun saveLastSyncStatus(status: String) {
        sharedPreferences.edit {
            putString(KEY_LAST_SYNC_STATUS, status)
        }
    }

    private suspend fun getLastSyncStatus(): String? {
        return sharedPreferences.getString(KEY_LAST_SYNC_STATUS, null)
    }

    override suspend fun saveLastSyncInfo(timestamp: Long, status: String) {
        saveLastSyncTime(timestamp)
        saveLastSyncStatus(status)
    }

    override suspend fun getLastSyncInfo(): Pair<Long?, String?> {
        val time = getLastSyncTime()
        val status = getLastSyncStatus()
        return Pair(time, status)
    }

    override suspend fun saveSyncInterval(interval: Long) {
        sharedPreferences.edit {
            putLong(KEY_SYNC_INTERVAL, interval)
        }
    }

    override suspend fun getSyncInterval(): Long {
        return sharedPreferences.getLong(KEY_SYNC_INTERVAL, 4L)
    }

    companion object {
        private const val SYNC_PREF = "sync_preferences"
        private const val KEY_LAST_SYNC_TIME = "last_sync_time"
        private const val KEY_LAST_SYNC_STATUS = "last_sync_status"
        private const val KEY_SYNC_INTERVAL = "sync_interval"
    }
}