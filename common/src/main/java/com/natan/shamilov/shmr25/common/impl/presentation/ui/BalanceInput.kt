package com.natan.shamilov.shmr25.common.impl.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun BalanceInput(
    balance: String,
    onBalanceChange: (String) -> Unit,
    isError: Boolean
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = balance,
            onValueChange = { input ->
                val filtered = input
                    .filter { it.isDigit() || it == '.' }
                    .let { raw ->
                        val parts = raw.split('.', limit = 2)
                        when (parts.size) {
                            1 -> parts[0]
                            2 -> {
                                val decimals = parts[1].take(2)
                                "${parts[0]}.$decimals"
                            }
                            else -> raw // fallback
                        }
                    }

                val dotCount = filtered.count { it == '.' }
                if (dotCount <= 1) {
                    onBalanceChange(filtered)
                }
            },
            label = { Text("Сумма на счёте") },
            isError = isError,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            singleLine = true,
            trailingIcon = {
                if (balance.isNotEmpty()) {
                    IconButton(onClick = { onBalanceChange("") }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Очистить",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        )
        if (isError) {
            Text(
                text = "Введите сумму",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}
