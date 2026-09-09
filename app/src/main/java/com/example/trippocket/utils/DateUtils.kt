package com.example.trippocket.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

private val shortDateFormatter =
    DateTimeFormatter.ofPattern("d MMM", Locale.ENGLISH)

private val fullDateFormatter =
    DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH)

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