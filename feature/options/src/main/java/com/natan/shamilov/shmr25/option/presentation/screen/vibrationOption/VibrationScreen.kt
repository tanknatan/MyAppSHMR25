package com.natan.shamilov.shmr25.option.presentation.screen.vibrationOption

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.natan.shamilov.shmr25.common.impl.presentation.ui.CustomTopAppBar
import com.natan.shamilov.shmr25.feature.option.presentation.components.ThemeCard
import com.natan.shamilov.shmr25.option.presentation.navigation.OptionsFlow

@Composable
fun VibrationScreen(onBackClick: () -> Unit) {
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

        VibrationContent(
            paddingValues = innerPadding,
        )
    }
}

@Composable
fun VibrationContent(paddingValues: PaddingValues) {
    var vibrationStatus by remember { mutableStateOf(true) }
    var selectedIndex by remember { mutableStateOf(0) }
    Column(
        modifier = Modifier
            .padding(paddingValues),
    ) {
        ThemeCard(
            title = "Vibration",
            isDarkTheme = vibrationStatus,
            onToggle = { vibrationStatus = !vibrationStatus }
        )
        Spacer(modifier = Modifier.size(32.dp))
        TextRadioButtonsRow(
            selectedIndex = selectedIndex,
            onSelectedIndexChange = { selectedIndex = it }
        )
    }
}

@Composable
fun TextRadioButtonsRow(
    selectedIndex: Int,
    onSelectedIndexChange: (Int) -> Unit,
    buttonSize: Dp = 70.dp,
    text: List<String> = listOf(
        "Short",
        "Medium",
        "Long",
    ),
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        text.forEachIndexed { index, item ->
            val isSelected = index == selectedIndex
            Box(
                modifier = Modifier
                    .size(buttonSize)
                    .background(
                        color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable { onSelectedIndexChange(index) }
            ) {
                Text(
                    text = item,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

