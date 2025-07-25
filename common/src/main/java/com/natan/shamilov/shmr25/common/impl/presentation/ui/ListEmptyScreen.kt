package com.natan.shamilov.shmr25.common.impl.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.natan.shamilov.shmr25.common.impl.presentation.ui.theme.localizedString
import androidx.compose.ui.unit.dp
import com.natan.shamilov.shmr25.common.R
import com.natan.shamilov.shmr25.common.impl.presentation.ui.theme.localizedString

@Composable
fun ListEmptyScreen(onRetry: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Box(

            contentAlignment = Alignment.Center
        ) {
            Text(text = localizedString(R.string.list_empty))
        }
        TextButton(
            onClick = onRetry,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = localizedString(R.string.retry))
        }
    }
}
