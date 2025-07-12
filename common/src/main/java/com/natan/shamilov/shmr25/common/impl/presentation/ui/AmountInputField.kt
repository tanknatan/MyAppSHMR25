package com.natan.shamilov.shmr25.common.impl.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.natan.shamilov.shmr25.common.R

@Composable
fun AmountInputField(
    amount: String,
    onAmountChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = stringResource(R.string.total_amount), // "Сумма"
    currency: String? = "₽",
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp)
            .drawBehind {
                // нижняя серая линия
                val strokeWidth = 1.dp.toPx()
                drawLine(
                    color = Color(0xFFE2DDE8),
                    start = Offset(0f, size.height - strokeWidth / 2),
                    end = Offset(size.width, size.height - strokeWidth / 2),
                    strokeWidth = strokeWidth
                )
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(Color.White)
                .drawBehind {
                    // нижняя серая линия
                    val strokeWidth = 1.dp.toPx()
                    drawLine(
                        color = Color(0xFFE2DDE8),
                        start = Offset(0f, size.height - strokeWidth / 2),
                        end = Offset(size.width, size.height - strokeWidth / 2),
                        strokeWidth = strokeWidth
                    )
                }
                .border(
                    width = 1.dp,
                    color = Color.Transparent,
                    shape = TextFieldDefaults.shape
                )
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = amount,
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

                                    else -> raw
                                }
                            }

                        if (filtered.count { it == '.' } <= 1) {
                            onAmountChange(filtered)
                        }
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        color = Color.Black,
                        textAlign = TextAlign.End
                    ),
                    modifier = Modifier.widthIn(min = 60.dp)
                )

                Spacer(Modifier.width(4.dp))

                if (currency != null) {
                    Text(
                        text = currency,
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black)
                    )
                }
            }
        }
    }
}
