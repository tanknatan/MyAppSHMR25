package com.natan.shamilov.shmr25.common.impl.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.natan.shamilov.shmr25.common.impl.presentation.ui.theme.PrimaryGreen

@Composable
fun PinKeyboard(
    onNumberClick: (String) -> Unit,
    onDelete: () -> Unit,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        val keys = listOf(
            listOf("1", "2", "3"),
            listOf("4", "5", "6"),
            listOf("7", "8", "9"),
            listOf("", "0", "<")
        )

        keys.forEach { row ->
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                row.forEach { key ->
                    Spacer(modifier = Modifier.width(15.dp))
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .testTag("pin_key_$key")
                            .clickable {
                                when (key) {
                                    "<" -> onDelete()
                                    "" -> {}
                                    else -> onNumberClick(key)
                                }
                            }
                            .background(Color.LightGray, CircleShape)
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = key,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                    Spacer(modifier = Modifier.width(15.dp))
                }
            }
        }
    }
}

@Composable
fun PinIndicators(
    currentPin: String,
    isError: Boolean,
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .testTag("pin_indicators"), horizontalArrangement = Arrangement.Center) {
        repeat(4) { i ->
            val filled = i < currentPin.length
            val borderColor = when {
                isError -> Color.Red
                filled -> PrimaryGreen
                else -> Color.Gray
            }

            Box(
                modifier = Modifier
                    .size(35.dp)
                    .testTag("pin_indicator_$i")
                    .border(
                        width = 6.dp,
                        color = borderColor,
                        shape = CircleShape
                    )
                    .padding(4.dp)
            )

            if (i != 3) {
                Spacer(modifier = Modifier.width(12.dp))
            }
        }
    }
}
