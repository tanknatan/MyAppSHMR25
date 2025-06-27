package com.natan.shamilov.shmr25.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

@Immutable
data class AppColors(
    @Stable
    val primary: Color,
    @Stable
    val secondary: Color,
    @Stable
    val borderGrey: Color
) {
//    val backgroundElevatedCardColors: CardColors
//        @Composable
//        get() = CardDefaults.elevatedCardColors(containerColor = background)
}
