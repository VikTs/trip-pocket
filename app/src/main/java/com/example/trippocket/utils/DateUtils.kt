package com.example.trippocket.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

val locale = Locale.getDefault()

private val shortDateFormatter =
    DateTimeFormatter.ofPattern("d MMM", locale)

private val fullDateFormatter =
    DateTimeFormatter.ofPattern("d MMM yyyy", locale)

private val dayOfWeekDateFormatter =
    DateTimeFormatter.ofPattern("EEEE, d MMM", locale)

private val timeFormatter =
    DateTimeFormatter.ofPattern("HH:mm")

private val dateTimeFormatter =
    DateTimeFormatter.ofPattern("d MMM HH:mm", locale)

private val accommodationDateTimeFormatter =
    android.icu.text.SimpleDateFormat(
        "d MMMM, HH:mm",
        locale
    )

fun formatTripDates(
    startDate: LocalDate,
    endDate: LocalDate
): String {
    return if (
        startDate.month == endDate.month &&
        startDate.year == endDate.year
    ) {
        "${startDate.dayOfMonth}–${endDate.format(shortDateFormatter)}"
    } else {
        "${startDate.format(shortDateFormatter)} — ${endDate.format(shortDateFormatter)}"
    }
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

fun formatAccommodationDateTime(
    dateTime: LocalDateTime,
): String {
    return accommodationDateTimeFormatter.format(
        Date.from(
            dateTime
                .atZone(ZoneId.systemDefault())
                .toInstant()
        )
    )
}