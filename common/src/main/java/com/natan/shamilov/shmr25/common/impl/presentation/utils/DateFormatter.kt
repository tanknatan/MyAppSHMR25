package com.natan.shamilov.shmr25.common.impl.presentation.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

fun toUtcIsoString(dateMillis: Long, time: LocalTime): String {
    val localDate = Instant.ofEpochMilli(dateMillis)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

    val dateTime = LocalDateTime.of(localDate, time)
    val zonedDateTime = dateTime.atZone(ZoneId.systemDefault())
        .withZoneSameInstant(ZoneOffset.UTC)

    return DateTimeFormatter.ISO_INSTANT.format(zonedDateTime.toInstant())
}

fun formatToIsoUtc(timestampMillis: Long): String {
    return Instant.ofEpochMilli(timestampMillis)
        .atOffset(ZoneOffset.UTC)
        .format(DateTimeFormatter.ISO_INSTANT)
}

fun String.extractDate(): String {
    val instant = Instant.parse(this)
    val dateTime = instant.atZone(ZoneOffset.UTC)
    return dateTime.toLocalDate().toString() // формат yyyy-MM-dd
}

fun String.extractTime(): String {
    val instant = Instant.parse(this)
    val dateTime = instant.atZone(ZoneOffset.UTC)
    return dateTime.toLocalTime().let {
        String.format("%02d:%02d", it.hour, it.minute)
    }
}

fun String.formatDateToMillis(): Long {
    return Instant.parse(this)
        .atZone(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()
}

fun String.toLocalTimeWithoutSeconds(): LocalTime {
    return Instant.parse(this)
        .atZone(ZoneId.systemDefault())
        .toLocalTime()
        .withSecond(0)
        .withNano(0)
}
