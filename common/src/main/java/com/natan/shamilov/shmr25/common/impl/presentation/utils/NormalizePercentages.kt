package com.natan.shamilov.shmr25.common.impl.presentation.utils

import kotlin.math.round

fun normalizePercentages(
    stats: List<Pair<Triple<Int, String, String>, Double>>,
    totalAmount: Double
): List<Triple<Triple<Int, String, String>, Double, Double>> {
    val raw = stats.map { (key, amount) ->
        val percent = if (totalAmount == 0.0) 0.0 else (amount / totalAmount) * 100
        Triple(key, amount, percent)
    }

    val minThreshold = 0.01
    val adjusted = raw.map { (key, amount, percent) ->
        val fixed = if (percent in 0.0..minThreshold && amount > 0) minThreshold else percent
        Triple(key, amount, fixed)
    }

    val totalAdjusted = adjusted.sumOf { it.third }

    return if (totalAdjusted <= 100.0) {
        adjusted.map { (key, amount, percent) ->
            Triple(key, amount, round(percent * 100) / 100)
        }
    } else {
        adjusted.map { (key, amount, percent) ->
            val scaled = (percent / totalAdjusted) * 100
            Triple(key, amount, round(scaled * 100) / 100)
        }
    }
}
