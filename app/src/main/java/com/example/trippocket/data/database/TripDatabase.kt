package com.example.trippocket.data.database

import androidx.room3.ColumnTypeConverters
import androidx.room3.Database
import androidx.room3.RoomDatabase
import com.example.trippocket.data.converter.LocalDateConverter
import com.example.trippocket.data.converter.TransportTypeConverter
import com.example.trippocket.data.dao.AccommodationDao
import com.example.trippocket.data.dao.NotificationDao
import com.example.trippocket.data.dao.TransportDao
import com.example.trippocket.data.dao.TransportDocumentDao
import com.example.trippocket.data.dao.TripDao
import com.example.trippocket.data.model.Accommodation
import com.example.trippocket.data.model.Notification
import com.example.trippocket.data.model.Transport
import com.example.trippocket.data.model.TransportDocument
import com.example.trippocket.data.model.Trip

@Database(
    entities = [
        Trip::class,
        Transport::class,
        Notification::class,
        Accommodation::class,
        TransportDocument::class,
    ],
    version = 1
)
@ColumnTypeConverters(
    LocalDateConverter::class,
    TransportTypeConverter::class
)
abstract class TripDatabase : RoomDatabase() {
    abstract fun tripDao(): TripDao
    abstract fun transportDao(): TransportDao
    abstract fun notificationDao(): NotificationDao
    abstract fun accommodationDao(): AccommodationDao
    abstract fun transportDocumentDao(): TransportDocumentDao
}