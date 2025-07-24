package com.natan.shamilov.shmr25.account.impl.data

import com.natan.shamilov.shmr25.common.impl.domain.entity.Transaction
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

fun List<Transaction>.toDateAmountMapWithZeros(): Map<String, Double> {
    val outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val today = LocalDate.now()
    val days = (0..30).map { today.minusDays(it.toLong()) }

    // Парсинг с учётом временной зоны
    val grouped = this.groupBy { transaction ->
        val instant = Instant.parse(transaction.createdAt)
        val date = instant.atZone(ZoneId.systemDefault()).toLocalDate()
        date.format(outputFormatter)
    }

    return days.associate { day ->
        val key = day.format(outputFormatter)
        val sum = grouped[key]?.sumOf { it.amount } ?: 0.0
        key to sum
    }.toSortedMap()
}
