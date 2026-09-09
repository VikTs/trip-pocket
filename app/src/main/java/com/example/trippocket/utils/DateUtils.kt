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

private val timeFormatter =
    DateTimeFormatter.ofPattern("HH:mm")

private val dateTimeFormatter =
    DateTimeFormatter.ofPattern("d MMM HH:mm")

fun formatTripDates(
    startDate: LocalDate,
    endDate: LocalDate
): String {
    return if (startDate.year == endDate.year) {
        "${startDate.format(shortDateFormatter)} — ${endDate.format(fullDateFormatter)}"
    } else {
        "${startDate.format(fullDateFormatter)} — ${endDate.format(fullDateFormatter)}"
    }
}

fun formatTripDate(date: LocalDate): String {
    return date.format(fullDateFormatter)
}

fun formatTripTime(date: LocalTime): String {
    return date.format(timeFormatter)
}

fun formatTripDateTime(date: LocalDateTime): String {
    return date.format(dateTimeFormatter)
}