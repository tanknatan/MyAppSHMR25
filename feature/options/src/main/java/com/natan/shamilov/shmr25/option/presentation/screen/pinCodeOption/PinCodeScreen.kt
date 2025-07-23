package com.natan.shamilov.shmr25.option.presentation.screen.pinCodeOption

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.Scaffold
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
import com.natan.shamilov.shmr25.common.impl.presentation.ui.CustomTopAppBar
import com.natan.shamilov.shmr25.option.presentation.navigation.OptionsFlow
import kotlinx.coroutines.delay

@Composable
fun PinCodeScreen(onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            CustomTopAppBar(
                OptionsFlow.PinCode.startIcone,
                OptionsFlow.PinCode.title,
                OptionsFlow.PinCode.endIcone,
                onBackOrCanselClick = { onBackClick() },
                onNavigateClick = { }
            )
        }
    ) { innerPadding ->

        PinCodeContent(
            paddingValues = innerPadding,
        )
    }
}

@Composable
fun PinCodeContent(paddingValues: PaddingValues) {
    var step by remember { mutableStateOf(1) } // 1 - ввод, 2 - подтверждение
    var pin by remember { mutableStateOf("") }
    var confirmPin by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isError by remember { mutableStateOf(false) }

    val currentPin = if (step == 1) pin else confirmPin
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = if (step == 1) "Придумайте PIN-код" else "Повторите PIN-код",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            PinIndicators(currentPin = currentPin, isError = isError)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            contentAlignment = Alignment.Center
        ) {
            PinKeyboard(
                onNumberClick = {
                    isError = false
                    errorMessage = null
                    if (currentPin.length < 4) {
                        if (step == 1) pin += it else confirmPin += it
                    }
                },
                onDelete = {
                    if (currentPin.isNotEmpty()) {
                        if (step == 1) pin = pin.dropLast(1) else confirmPin = confirmPin.dropLast(1)
                    }
                }
            )
        }

        errorMessage?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = it, color = Color.Red)
        }

        if (currentPin.length == 4) {
            LaunchedEffect(currentPin) {
                delay(300)
                if (step == 1) {
                    errorMessage = null
                    step = 2
                } else {
                    if (pin == confirmPin) {
                        errorMessage = null
                        // onPinSet(pin)
                    } else {
                        errorMessage = "PIN-коды не совпадают"
                        isError = true
                        pin = ""
                        confirmPin = ""
                        step = 1
                    }
                }
            }
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
