package com.natan.shamilov.shmr25.common.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalConfiguration

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryGreen,
    secondary = SecondaryGreen,
    // borderGrey = BorderGrey,
    background = Black
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryGreen,
    secondary = SecondaryGreen,
    // borderGrey = BorderGrey,
    background = White

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
     */
)

@Composable
fun MyAppSHMR25Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            // val context = LocalContext.current
            if (darkTheme) LightColorScheme else LightColorScheme
        }

        darkTheme -> LightColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Immutable
object Theme {

//    val typography: AppTypography
//        @ReadOnlyComposable
//        @Composable
//        get() = LocalAppTypography.current

    val colors: AppColors
        @ReadOnlyComposable
        @Composable
        get() = LocalAppColors.current

//    val shapes: AppShapes
//        @ReadOnlyComposable
//        @Composable
//        get() = LocalAppShapes.current
//
//    val elevations: AppElevations
//        @ReadOnlyComposable
//        @Composable
//        get() = LocalAppElevations.current
}

@Composable
fun Theme(
    screenWidth: Float = LocalConfiguration.current.screenWidthDp.toFloat(),
    appTheme: AppTheme = AppThemeImpl,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        // LocalAppTypography provides appTheme.typography,
        LocalAppColors provides appTheme.colors
        // LocalAppShapes provides appTheme.shapes,
        // LocalAppElevations provides appTheme.elevations
    ) {
        // ProvideTextStyle(value = Theme.typography.body, content = content)
    }
}

// val LocalAppTypography = staticCompositionLocalOf<AppTypography> {
//    error("No AppTypography provided")
// }

val LocalAppColors = staticCompositionLocalOf<AppColors> {
    error("No AppColors provided")
}

// val LocalAppShapes = staticCompositionLocalOf<AppShapes> {
//    error("No AppShapes provided")
// }
//
// val LocalAppElevations = staticCompositionLocalOf<AppElevations> {
//    error("No AppElevations provided")
// }
