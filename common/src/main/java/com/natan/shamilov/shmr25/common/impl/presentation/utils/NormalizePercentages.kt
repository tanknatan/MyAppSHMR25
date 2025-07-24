package com.natan.shamilov.shmr25.common.impl.presentation.utils

import kotlin.math.round

fun normalizePercentages(
    stats: List<Pair<Triple<Int, String, String>, Double>>,
    totalAmount: Double
): List<Triple<Triple<Int, String, String>, Double, Double>> {
    if (totalAmount == 0.0) {
        return stats.map { (key, _) -> Triple(key, 0.0, 0.0) }
    }

    val raw = stats.map { (key, amount) ->
        val percent = (amount / totalAmount) * 100
        Triple(key, amount, percent)
    }

    val minPercent = 1.0
    val (belowMin, aboveMin) = raw.partition { it.third < minPercent && it.second > 0 }

    val adjustedBelowMin = belowMin.map { (key, amount, _) ->
        Triple(key, amount, minPercent)
    }

    val remainingPercent = 100.0 - adjustedBelowMin.sumOf { it.third }
    val totalAbove = aboveMin.sumOf { it.third }

    val adjustedAboveMin = aboveMin.map { (key, amount, percent) ->
        val scaledPercent = if (totalAbove == 0.0) 0.0 else (percent / totalAbove) * remainingPercent
        Triple(key, amount, round(scaledPercent * 100) / 100)
    }

    val adjusted = (adjustedBelowMin + adjustedAboveMin).map { (key, amount, percent) ->
        Triple(key, amount, round(percent * 100) / 100)
    }

    return adjusted
}

