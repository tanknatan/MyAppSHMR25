package com.natan.shamilov.shmr25.option.impl.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.natan.shamilov.shmr25.common.impl.presentation.ui.theme.dep
import com.natan.shamilov.shmr25.common.impl.presentation.ui.theme.rodotoFont

@Composable
fun ThemeCard(
    title: String,
    isDarkTheme: Boolean,
    onToggle: (Boolean) -> Unit,
) {
    val borderColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dep),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            hoveredElevation = 0.dp,
            focusedElevation = 0.dp,
            draggedElevation = 0.dp,
            disabledElevation = 0.dp
        ),
        shape = RectangleShape
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    val strokeWidth = 1.dp.toPx()
                    val y = size.height - strokeWidth / 2

                    drawLine(
                        color = borderColor,
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = strokeWidth
                    )
                }
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 16.sp,
                    fontFamily = rodotoFont

                )
            }

            Switch(
                checked = isDarkTheme,
                modifier = Modifier.testTag("theme_toggle_switch"),
                onCheckedChange = onToggle,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = White,
                    uncheckedThumbColor = Color(0xFF79747E),
                    checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                    uncheckedTrackColor = Color(0xFFE6E0E9)
                )
            )
        }
    }
}
