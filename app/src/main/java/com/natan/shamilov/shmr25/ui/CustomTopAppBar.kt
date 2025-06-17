package com.natan.shamilov.shmr25.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.natan.shamilov.shmr25.ui.theme.dep
import com.natan.shamilov.shmr25.ui.theme.rodotoFont

@Composable
fun CustomTopAppBar(
    startIcone: Int?,
    title: Int?,
    endIcone: Int?,
    onBackOrCanselClick: (() -> Unit)?,
    onNavigateClick: (() -> Unit)?,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .windowInsetsPadding(WindowInsets.statusBars)
            .height(64.dep),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (startIcone == null || onBackOrCanselClick == null) {
            Box(
                modifier = Modifier.size(48.dep)
            )
        } else {
            IconButton(
                onClick = { onBackOrCanselClick() },
                modifier = Modifier.size(48.dep)

            ) {
                Image(
                    painter = painterResource(startIcone),
                    contentDescription = null,
                    modifier = Modifier.size(40.dep)
                )
            }
        }
        if (title != null) {
            Text(
                modifier = Modifier,
                textAlign = TextAlign.Center,
                text = stringResource(title),
                fontSize = 22.sp,
                fontFamily = rodotoFont,
            )
        }
        if (endIcone == null || onNavigateClick == null) {
            Box(
                modifier = Modifier
                    .size(48.dep)
            )
        } else {
            IconButton(
                onClick = { onNavigateClick() },
                modifier = Modifier.size(48.dep)

            ) {
                Image(
                    painter = painterResource(endIcone),
                    contentDescription = null,
                    modifier = Modifier.size(40.dep)
                )
            }
        }
    }
}
