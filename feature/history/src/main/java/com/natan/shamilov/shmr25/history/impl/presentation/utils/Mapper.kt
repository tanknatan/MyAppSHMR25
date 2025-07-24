package com.natan.shamilov.shmr25.history.impl.presentation.utils

import androidx.compose.ui.graphics.Color
import com.natan.shamilov.shmr25.history.impl.presentation.screen.analysis.AnalyticsUiModel

fun AnalyticsUiModel.toPieChartData(): Map<String, Float> {
    return categoryStats.associate { stat ->
        "${stat.emoji} ${stat.categoryName}" to stat.percent.toFloat()
    }
}

fun generateColorsHSV(count: Int): List<Color> {
    val colors = mutableListOf<Color>()
    val step = 360f / count
    for (i in 0 until count) {
        val hue = step * i
        val hsv = floatArrayOf(hue, 0.8f, 0.9f) // насыщенность и яркость фиксированы
        val colorInt = android.graphics.Color.HSVToColor(hsv)
        colors.add(Color(colorInt))
    }
    return colors
}
