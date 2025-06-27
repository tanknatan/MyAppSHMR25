package com.natan.shamilov.shmr25.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.natan.shamilov.shmr25.R

@Composable
fun MyFloatingActionButton(onFabClick: () -> Unit) {
    FloatingActionButton(
        onClick = { onFabClick() },
        shape = CircleShape,
        containerColor = MaterialTheme.colorScheme.primary,
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 0.dp,
            focusedElevation = 0.dp,
            hoveredElevation = 0.dp
        )
    ) {
        Image(painter = painterResource(R.drawable.ic_plus), contentDescription = null)
    }
}