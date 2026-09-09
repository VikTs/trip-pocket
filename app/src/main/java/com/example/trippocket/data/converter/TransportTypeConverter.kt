package com.example.trippocket.data.converter

import androidx.room3.ColumnTypeConverter
import com.example.trippocket.data.model.TransportType

class TransportTypeConverter {
    @ColumnTypeConverter
    fun fromTransportType(value: TransportType): String {
        return value.name
    }

    @ColumnTypeConverter
    fun toTransportType(value: String): TransportType {
        return TransportType.valueOf(value)
    }
}