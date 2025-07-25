package com.natan.shamilov.shmr25.option.impl.presentation.screen.vibrationOption

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.natan.shamilov.shmr25.common.impl.domain.entity.HapticType
import com.natan.shamilov.shmr25.common.impl.presentation.LocalViewModelFactory
import com.natan.shamilov.shmr25.common.impl.presentation.ui.CustomTopAppBar
import com.natan.shamilov.shmr25.option.impl.presentation.components.ThemeCard
import com.natan.shamilov.shmr25.option.impl.presentation.navigation.OptionsFlow

@Composable
fun VibrationScreen(
    onBackClick: () -> Unit,
    viewModel: VibrationViewModel = viewModel(factory = LocalViewModelFactory.current),
) {
    Scaffold(
        topBar = {
            CustomTopAppBar(
                OptionsFlow.Vibration.startIcone,
                OptionsFlow.Vibration.title,
                OptionsFlow.Vibration.endIcone,
                onBackOrCanselClick = { onBackClick() },
                onNavigateClick = { }
            )
        }
    ) { innerPadding ->
        val hapticType = viewModel.hapticType.collectAsStateWithLifecycle()
        val hapticEnabledState = viewModel.hapticEnabledState.collectAsStateWithLifecycle()

        VibrationContent(
            paddingValues = innerPadding,
            hapticType = hapticType.value,
            onSelectedChange = { type ->
                viewModel.setHapticType(type)
            },
            isEnabled = hapticEnabledState.value,
            onChangeVibration = { viewModel.setHapticEnabledState(!hapticEnabledState.value) }
        )
    }
}

@Composable
fun VibrationContent(
    paddingValues: PaddingValues,
    hapticType: HapticType,
    onSelectedChange: (HapticType) -> Unit,
    isEnabled: Boolean,
    onChangeVibration: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(paddingValues),
    ) {
        ThemeCard(
            title = "Vibration",
            isDarkTheme = isEnabled,
            onToggle = { onChangeVibration() }
        )
        Spacer(modifier = Modifier.size(32.dp))
        TextRadioButtonsRow(
            selected = hapticType,
            onSelectedChange = { type ->
                onSelectedChange(type)
            },
            isEnabled = isEnabled
        )
    }
}

@Composable
fun TextRadioButtonsRow(
    selected: HapticType,
    onSelectedChange: (HapticType) -> Unit,
    buttonSize: Dp = 70.dp,
    isEnabled: Boolean,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        HapticType.entries.forEach { hapticType ->
            val isSelected = hapticType == selected && isEnabled
            Box(
                modifier = Modifier
                    .size(buttonSize)
                    .background(
                        color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Gray,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable { onSelectedChange(hapticType) }
            ) {
                Text(
                    text = hapticType.value,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

