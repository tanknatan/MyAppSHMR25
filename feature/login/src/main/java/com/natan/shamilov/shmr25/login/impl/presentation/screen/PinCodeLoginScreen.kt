package com.natan.shamilov.shmr25.login.impl.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.natan.shamilov.shmr25.common.impl.presentation.LocalViewModelFactory
import com.natan.shamilov.shmr25.common.impl.presentation.ui.PinIndicators
import com.natan.shamilov.shmr25.common.impl.presentation.ui.PinKeyboard

@Composable
fun PinCodeLoginScreen(
    onContinue: () -> Unit,
    viewModel: LoginViewModel = viewModel(factory = LocalViewModelFactory.current),
) {
    var enteredPin by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    LaunchedEffect(enteredPin) {
        if (enteredPin.length == 4) {
            if (viewModel.checkPassword(enteredPin)) {
                isError = false
                onContinue()
            } else {
                isError = true
                enteredPin = ""
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
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
    }
}

