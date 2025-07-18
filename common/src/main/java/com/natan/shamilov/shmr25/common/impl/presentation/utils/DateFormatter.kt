package com.natan.shamilov.shmr25.common.impl.presentation.utils

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
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

fun getUtcDayBounds(date: LocalDate): Pair<String, String> {
    val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    val start = date.atStartOfDay().atOffset(ZoneOffset.UTC).format(formatter)
    val end = date.atTime(LocalTime.MAX).atOffset(ZoneOffset.UTC).format(formatter)
    return start to end
}

fun toStartOfDayIso(dateString: String): String {
    val date = LocalDate.parse(dateString)
    val zdt: ZonedDateTime = date
        .atStartOfDay()
        .atZone(ZoneOffset.UTC)

    return zdt.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
}

fun toEndOfDayIso(dateString: String): String {
    val date = LocalDate.parse(dateString) // "2025-07-18"
    return date
        .atTime(LocalTime.MAX) // 23:59:59.999999
        .atOffset(ZoneOffset.UTC)
        .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
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
