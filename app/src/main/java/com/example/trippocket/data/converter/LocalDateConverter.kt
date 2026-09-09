package com.example.trippocket.data.converter

import androidx.room3.ColumnTypeConverter
import java.time.LocalDate
import java.time.LocalDateTime

class LocalDateConverter {
    @ColumnTypeConverter
    fun fromLocalDate(date: LocalDate): String {
        return date.toString()
    }

    @ColumnTypeConverter
    fun toLocalDate(value: String): LocalDate {
        return LocalDate.parse(value)
    }

    @ColumnTypeConverter
    fun fromLocalDateTime(value: LocalDateTime): String {
        return value.toString()
    }

    @ColumnTypeConverter
    fun toLocalDateTime(value: String): LocalDateTime {
        return LocalDateTime.parse(value)
    }
}