package com.natan.shamilov.shmr25.login.impl.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun PinCodeLoginScreen(onContinue: () -> Unit, correctPin: String = "1234") {
    var enteredPin by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Введите PIN-код",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(24.dp))
        PinIndicators(currentPin = enteredPin, isError = isError)
        Spacer(modifier = Modifier.height(24.dp))

        PinKeyboard(
            onNumberClick = {
                if (enteredPin.length < 4) {
                    isError = false
                    enteredPin += it
                }
            },
            onDelete = {
                if (enteredPin.isNotEmpty()) {
                    enteredPin = enteredPin.dropLast(1)
                }
            }
        )

        if (enteredPin.length == 4) {
            LaunchedEffect(enteredPin) {
                delay(300)
                if (enteredPin == correctPin) {
                    isError = false
                    onContinue()
                } else {
                    isError = true
                    enteredPin = ""
                }
            }
        }

        if (isError) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Неверный PIN-код", color = Color.Red)
        }
    }
}

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
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        repeat(4) { i ->
            val filled = i < currentPin.length
            val borderColor = when {
                isError -> Color.Red
                filled -> MaterialTheme.colorScheme.primary
                else -> Color.Gray
            }

            Box(
                modifier = Modifier
                    .size(35.dp)
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

