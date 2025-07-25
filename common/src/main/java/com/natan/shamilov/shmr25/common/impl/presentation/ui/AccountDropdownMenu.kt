package com.natan.shamilov.shmr25.common.impl.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.natan.shamilov.shmr25.common.R
import com.natan.shamilov.shmr25.common.impl.domain.entity.Account
import com.natan.shamilov.shmr25.common.impl.presentation.ui.theme.localizedString
import com.natan.shamilov.shmr25.common.impl.presentation.ui.theme.rodotoFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountDropdownMenu(
    accounts: List<Account>,
    selectedAccount: Account?,
    onAccountSelected: (Account) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .drawBehind {
                // нижняя серая линия
                val strokeWidth = 1.dp.toPx()
                drawLine(
                    color = Color(0xFFE2DDE8),
                    start = Offset(0f, size.height - strokeWidth / 2),
                    end = Offset(size.width, size.height - strokeWidth / 2),
                    strokeWidth = strokeWidth
                )
            },

    ) {
        Box(
            modifier = Modifier
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
            TextField(
                value = selectedAccount?.name ?: localizedString(R.string.choose_account),
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                modifier = modifier
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .fillMaxWidth()
                    .height(70.dp)
                    .menuAnchor()
                    .drawBehind {
                        // нижняя серая линия
                        val strokeWidth = 1.dp.toPx()
                        drawLine(
                            color = Color(0xFFE2DDE8),
                            start = Offset(0f, size.height - strokeWidth / 2),
                            end = Offset(size.width, size.height - strokeWidth / 2),
                            strokeWidth = strokeWidth
                        )
                    },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    disabledTextColor = MaterialTheme.colorScheme.secondaryContainer,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    textAlign = TextAlign.End,
                    color = Color.Black
                )
            )
            Text(
                text = localizedString(R.string.account),
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
        }

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 300.dp)
            ) {
                val scrollState = rememberScrollState()
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState)
                ) {
                    accounts.forEach { account ->
                        DropdownMenuItem(
                            text = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(30.dp)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.primary),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            "\uD83D\uDCB0",
                                            fontSize = 20.sp,
                                            fontFamily = rodotoFont
                                        )
                                    }

                                    Spacer(Modifier.width(8.dp))

                                    Text(
                                        text = account.name,
                                        fontSize = 16.sp,
                                        fontFamily = rodotoFont,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            },
                            onClick = {
                                onAccountSelected(account)
                                expanded = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}
