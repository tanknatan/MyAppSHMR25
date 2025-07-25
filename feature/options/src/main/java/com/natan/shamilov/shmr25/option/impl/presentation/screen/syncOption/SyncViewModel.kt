package com.natan.shamilov.shmr25.option.impl.presentation.screen.syncOption

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.common.api.SyncPreferencesProvider
import com.natan.shamilov.shmr25.common.api.WorkManagerProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SyncViewModel @Inject constructor(
    private val syncPreferencesProvider: SyncPreferencesProvider,
    private val workManagerProvider: WorkManagerProvider
): ViewModel() {

    private val _syncInterval = MutableStateFlow(2)
    val syncInterval = _syncInterval.asStateFlow()

    init {
        getSyncInterval()
    }

    private fun getSyncInterval()  {
        viewModelScope.launch(Dispatchers.IO) {
            _syncInterval.value = syncPreferencesProvider.getSyncInterval().toInt()
        }
    }

    fun setSyncInterval(interval: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            syncPreferencesProvider.saveSyncInterval(interval.toLong())
            getSyncInterval()
            workManagerProvider.schedulePeriodicSync()
        }
    }
}