package com.natan.shamilov.shmr25.common.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

/**
 * Интерфейс темы приложения.
 * Ответственность: Определение контракта для темы приложения,
 * включая цветовую схему и другие стилевые параметры.
 */
@Immutable
interface AppTheme {
    /**
     * Цветовая схема приложения
     */
    @Stable
    val colors: AppColors
//    @Stable
//    val shapes: AppShapes
//    @Stable
//    val typography: AppTypography
//    @Stable
//    val elevations: AppElevations
}
