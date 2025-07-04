package com.natan.shamilov.shmr25.common.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Поле ввода для названия счета.
 * Отображает текстовое поле с возможностью очистки и индикацией ошибки.
 * Включает кнопку очистки, когда поле не пустое.
 *
 * @param accountName Текущее значение названия счета
 * @param onNameChange Callback для обработки изменения текста
 * @param isError Флаг, указывающий на наличие ошибки валидации
 */
@Composable
fun AccountNameInput(
    accountName: String,
    onNameChange: (String) -> Unit,
    isError: Boolean
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = accountName,
            onValueChange = onNameChange,
            label = { Text("Имя счёта") },
            isError = isError,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                if (accountName.isNotEmpty()) {
                    IconButton(onClick = { onNameChange("") }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Очистить",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            },
            singleLine = true
        )
        if (isError) {
            Text(
                text = "Имя счёта обязательно",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}
