package com.example.trippocket.data.converter

import androidx.room3.ColumnTypeConverter
import java.time.LocalDate

class LocalDateConverter {
    @ColumnTypeConverter
    fun fromLocalDate(date: LocalDate): String {
        return date.toString()
    }

    @ColumnTypeConverter
    fun toLocalDate(value: String): LocalDate {
        return LocalDate.parse(value)
    }
}