package com.example.trippocket.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

private val tripDateFormatter = DateTimeFormatter.ofPattern(
    "d MMM yyyy",
    Locale.ENGLISH
)

fun LocalDate.toTripDateString(): String {
    return format(tripDateFormatter)
}