package com.example.trippocket.utils

import android.content.Context
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

private val shortDatePattern = "d MMM"
private val fullDatePattern = "d MMM yyyy"
private val dayOfWeekDatePattern = "EEEE, d MMM"
private val timePattern = "HH:mm"
private val dateTimePattern = "d MMM HH:mm"
private val accommodationDateTimePattern = "d MMMM, HH:mm"

private val timeFormatter =
    DateTimeFormatter.ofPattern(timePattern)

fun formatTripDates(
    context: Context,
    startDate: LocalDate,
    endDate: LocalDate
): String {
    val locale = getAppLocale(context)

    val formatter =
        DateTimeFormatter.ofPattern(shortDatePattern, locale)

    return if (
        startDate.month == endDate.month &&
        startDate.year == endDate.year
    ) {
        "${startDate.dayOfMonth}–${endDate.format(formatter)}"
    } else {
        "${startDate.format(formatter)} — ${endDate.format(formatter)}"
    }
}

fun formatTripDate(
    context: Context,
    date: LocalDate
): String {
    val locale = getAppLocale(context)

    val formatter =
        DateTimeFormatter.ofPattern(fullDatePattern, locale)

    return date.format(formatter)
}

fun formatDayOfWeekDate(
    context: Context,
    date: LocalDate
): String {
    val locale = getAppLocale(context)

    val formatter =
        DateTimeFormatter.ofPattern(dayOfWeekDatePattern, locale)

    return date.format(formatter)
}

fun formatTripTime(
    time: LocalTime
): String =
    time.format(timeFormatter)

fun formatTripTime(
    time: LocalDateTime
): String =
    time.format(timeFormatter)

fun formatTripDateTime(
    context: Context,
    dateTime: LocalDateTime
): String {
    val locale = getAppLocale(context)

    val formatter =
        DateTimeFormatter.ofPattern(dateTimePattern, locale)

    return dateTime.format(formatter)
}

fun formatAccommodationDateTime(
    context: Context,
    dateTime: LocalDateTime
): String {
    val locale = getAppLocale(context)

    val formatter =
        android.icu.text.SimpleDateFormat(
            accommodationDateTimePattern,
            locale
        )

    return formatter.format(
        Date.from(
            dateTime
                .atZone(ZoneId.systemDefault())
                .toInstant()
        )
    )
}