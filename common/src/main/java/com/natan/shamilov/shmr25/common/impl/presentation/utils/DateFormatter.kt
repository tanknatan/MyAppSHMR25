package com.natan.shamilov.shmr25.common.impl.presentation.utils

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun Long.formatDateToString(): String {
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return dateFormatter.format(Date(this))
}

fun getLocalTime(): String {
    val currentTime = LocalTime.now()
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    val formattedTime = currentTime.format(formatter)
    return formattedTime
}

fun LocalTime.formatToString(): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return this.format(formatter)
}

fun String.toLocalTime(): LocalTime {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return LocalTime.parse(this, formatter)
}

fun toUtcIsoString(dateMillis: Long, time: LocalTime): String {
    val localDate = Instant.ofEpochMilli(dateMillis)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

    val dateTime = LocalDateTime.of(localDate, time)
    val zonedDateTime = dateTime.atZone(ZoneId.systemDefault())
        .withZoneSameInstant(ZoneOffset.UTC)

    return DateTimeFormatter.ISO_INSTANT.format(zonedDateTime.toInstant())
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


