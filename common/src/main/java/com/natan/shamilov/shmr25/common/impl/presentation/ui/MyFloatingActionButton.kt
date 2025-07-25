package com.natan.shamilov.shmr25.common.impl.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.natan.shamilov.shmr25.common.R

/**
 * Кастомная плавающая кнопка действия (FAB).
 * Отображает круглую кнопку с иконкой плюса в цветах приложения.
 * Не имеет тени для соответствия плоскому дизайну.
 *
 * @param onFabClick Callback, вызываемый при нажатии на кнопку
 */
@Composable
fun MyFloatingActionButton(onFabClick: () -> Unit) {
    FloatingActionButton(
        onClick = { onFabClick() },
        shape = CircleShape,
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 0.dp,
            focusedElevation = 0.dp,
            hoveredElevation = 0.dp
        )
    ) {
        Image(painter = painterResource(R.drawable.ic_plus), contentDescription = null)
    }
}
