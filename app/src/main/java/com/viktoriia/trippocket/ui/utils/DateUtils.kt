package com.viktoriia.trippocket.ui.utils

import android.content.Context
import android.icu.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

private val shortMonthPattern = "MMM"
private val shortDatePattern = "d MMM"
private val fullDatePattern = "d MMM yyyy"
private val dayOfWeekDatePattern = "EEEE, d MMM"
private val timePattern = "HH:mm"
private val datePattern = "d MMMM"
private val dateTimePattern = "d MMMM, HH:mm"

private val timeFormatter =
    DateTimeFormatter.ofPattern(timePattern)

fun formatDatesRange(
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

fun formatDatesRangeWithYear(
    context: Context,
    startDate: LocalDate,
    endDate: LocalDate
): String {
    val locale = getAppLocale(context)

    val shortFormatter = DateTimeFormatter.ofPattern(shortDatePattern, locale)
    val fullFormatter = DateTimeFormatter.ofPattern(fullDatePattern, locale)

    return when {
        startDate.year != endDate.year -> {
            "${startDate.format(fullFormatter)} — ${endDate.format(fullFormatter)}"
        }

        startDate.month == endDate.month -> {
            "${startDate.dayOfMonth}–${endDate.format(fullFormatter)}"
        }

        else -> {
            "${startDate.format(shortFormatter)} — ${endDate.format(fullFormatter)}"
        }
    }
}

fun formatFullDate(
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

fun formatTime(
    time: LocalTime
): String =
    time.format(timeFormatter)
fun formatTime(
    time: LocalDateTime
): String =
    time.format(timeFormatter)

fun formatMonth(
    context: Context,
    date: LocalDate
): String {
    val locale = getAppLocale(context)

    val formatter =
        DateTimeFormatter.ofPattern(shortMonthPattern, locale)

    return date.format(formatter)
}

fun formatDate(
    context: Context,
    dateTime: LocalDateTime
): String {
    val locale = getAppLocale(context)

    val formatter =
        SimpleDateFormat(
            datePattern,
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

fun formatDateTime(
    context: Context,
    dateTime: LocalDateTime
): String {
    val locale = getAppLocale(context)

    val formatter =
        SimpleDateFormat(
            dateTimePattern,
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