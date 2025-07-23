package com.natan.shamilov.shmr25.option.presentation.screen.syncOption

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.natan.shamilov.shmr25.common.impl.presentation.ui.CustomTopAppBar
import com.natan.shamilov.shmr25.option.presentation.navigation.OptionsFlow

@Composable
fun SyncScreen(onBackClick: () -> Unit) {
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

        SyncContent(
            paddingValues = innerPadding,
        )
    }
}

@Composable
fun SyncContent(paddingValues: PaddingValues) {
    var syncIntervalHours by remember { mutableStateOf(2f) }
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Интервал синхронизации (в часах)", style = MaterialTheme.typography.titleMedium)
        Text("${syncIntervalHours.toInt()} ч")
        Box(modifier = Modifier.fillMaxWidth(0.8f)) {
            Slider(
                value = syncIntervalHours,
                onValueChange = { syncIntervalHours = it },
                valueRange = 1f..24f,
                steps = 22
            )
        }
    }
}
