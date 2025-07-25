package com.natan.shamilov.shmr25.option.impl.presentation.screen.pinCodeOption

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.natan.shamilov.shmr25.common.impl.presentation.LocalViewModelFactory
import com.natan.shamilov.shmr25.common.impl.presentation.ui.CustomTopAppBar
import com.natan.shamilov.shmr25.common.impl.presentation.ui.PinIndicators
import com.natan.shamilov.shmr25.common.impl.presentation.ui.PinKeyboard
import com.natan.shamilov.shmr25.common.impl.presentation.ui.theme.localizedString
import com.natan.shamilov.shmr25.option.impl.presentation.navigation.OptionsFlow
import com.natan.shamilov.shmr25.options.R
import kotlinx.coroutines.delay

@Composable
fun PinCodeScreen(
    onBackClick: () -> Unit,
    viewModel: PinCodeViewModel = viewModel(factory = LocalViewModelFactory.current),
) {
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
            onSetPinCode = { viewModel.setPinCode(it, onBackClick) }
        )
    }
}

@Composable
fun PinCodeContent(paddingValues: PaddingValues, onSetPinCode: (String) -> Unit) {
    var step by remember { mutableStateOf(1) } // 1 - ввод, 2 - подтверждение
    var pin by remember { mutableStateOf("") }
    var confirmPin by remember { mutableStateOf("") }
    val errorMessage by remember { mutableStateOf<Int>(R.string.pin_codes_do_not_match) }
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
                text = if (step == 1) localizedString(R.string.create_pin_code) else localizedString(R.string.repeat_pin_code),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            PinIndicators(currentPin = currentPin, isError = isError)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            if (errorMessage != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = localizedString(errorMessage), color = Color.Red)
            }
            PinKeyboard(
                onNumberClick = {
                    isError = false
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

        if (currentPin.length == 4) {
            LaunchedEffect(currentPin) {
                delay(300)
                if (step == 1) {
                    step = 2
                } else {
                    if (pin == confirmPin) {
                        onSetPinCode(pin)
                    } else {
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

