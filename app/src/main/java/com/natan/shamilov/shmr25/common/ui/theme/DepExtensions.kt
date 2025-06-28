package com.natan.shamilov.shmr25.common.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private const val FULL_WIDTH_FIGMA_LAYOUT = 412
private const val FULL_HEIGHT_FIGMA_LAYOUT = 906
private const val RELATIVE_SIZE_FIGMA_LAYOUT =
    ((FULL_WIDTH_FIGMA_LAYOUT + FULL_HEIGHT_FIGMA_LAYOUT) / 2)

@Composable
private fun rememberRelativePercent(): Float {
    val configuration = LocalConfiguration.current

    return remember(configuration) {
        val widthDp = configuration.screenWidthDp
        val heightDp = configuration.screenHeightDp
        val relativeScreen = (widthDp.toFloat() + heightDp.toFloat()) / 2
        relativeScreen / RELATIVE_SIZE_FIGMA_LAYOUT
    }
}

val Number.dep: Dp
    @Composable
    get() {
        val relativePercent = rememberRelativePercent()
        return remember(relativePercent) { (toFloat() * relativePercent).dp }
    }
