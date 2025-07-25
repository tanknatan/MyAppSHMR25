package com.natan.shamilov.shmr25.option.impl.presentation.screen.syncOption

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.natan.shamilov.shmr25.common.impl.presentation.LocalViewModelFactory
import com.natan.shamilov.shmr25.common.impl.presentation.ui.CustomTopAppBar
import com.natan.shamilov.shmr25.option.impl.presentation.navigation.OptionsFlow

@Composable
fun SyncScreen(onBackClick: () -> Unit, viewModel: SyncViewModel = viewModel(factory = LocalViewModelFactory.current)) {
    Scaffold(
        topBar = {
            CustomTopAppBar(
                OptionsFlow.Sync.startIcone,
                OptionsFlow.Sync.title,
                OptionsFlow.Sync.endIcone,
                onBackOrCanselClick = { onBackClick() },
                onNavigateClick = { }
            )
        }
    ) { innerPadding ->
        val syncInterval by viewModel.syncInterval.collectAsStateWithLifecycle()
        SyncContent(
            paddingValues = innerPadding,
            syncInterval = syncInterval,
            onSyncIntervalChange = { viewModel.setSyncInterval(it) }
        )
    }
}

@Composable
fun SyncContent(paddingValues: PaddingValues, syncInterval: Int, onSyncIntervalChange: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Интервал синхронизации (в часах)", style = MaterialTheme.typography.titleMedium)
        Text("${syncInterval.toInt()} ч")
        Box(modifier = Modifier.fillMaxWidth(0.8f)) {
            Slider(
                value = syncInterval.toFloat(),
                onValueChange = { onSyncIntervalChange(it.toInt()) },
                valueRange = 1f..24f,
                steps = 22,
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colorScheme.primaryContainer,
                    activeTrackColor = MaterialTheme.colorScheme.primaryContainer,
                    inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer
                )
            )
        }
    }
}
