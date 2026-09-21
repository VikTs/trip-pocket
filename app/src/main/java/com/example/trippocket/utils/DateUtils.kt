package com.example.trippocket.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

private val shortDateFormatter =
    DateTimeFormatter.ofPattern("d MMM", Locale.ENGLISH)

private val fullDateFormatter =
    DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH)

private val dayOfWeekDateFormatter =
    DateTimeFormatter.ofPattern("EEEE, MMM d", Locale.ENGLISH)

private val timeFormatter =
    DateTimeFormatter.ofPattern("HH:mm")

private val dateTimeFormatter =
    DateTimeFormatter.ofPattern("d MMM HH:mm", Locale.ENGLISH)

fun formatTripDates(
    startDate: LocalDate,
    endDate: LocalDate
): String {
    return "${startDate.format(shortDateFormatter)} — ${endDate.format(shortDateFormatter)}"
}

fun formatTripDate(date: LocalDate): String {
    return date.format(fullDateFormatter)
}

fun formatDayOfWeekDate(date: LocalDate): String {
    return date.format(dayOfWeekDateFormatter)
}

fun formatTripTime(time: LocalTime): String =
    time.format(timeFormatter)
fun formatTripTime(time: LocalDateTime): String =
    time.format(timeFormatter)

fun formatTripDateTime(date: LocalDateTime): String {
    return date.format(dateTimeFormatter)
}