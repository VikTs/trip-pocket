package com.example.trippocket.data.database

import androidx.room3.ColumnTypeConverters
import androidx.room3.Database
import androidx.room3.RoomDatabase
import com.example.trippocket.data.converter.LocalDateConverter
import com.example.trippocket.data.converter.TransportTypeConverter
import com.example.trippocket.data.dao.AccommodationDao
import com.example.trippocket.data.dao.AccommodationDocumentDao
import com.example.trippocket.data.dao.AccommodationNotificationDao
import com.example.trippocket.data.dao.TransportDao
import com.example.trippocket.data.dao.TransportDocumentDao
import com.example.trippocket.data.dao.TransportNotificationDao
import com.example.trippocket.data.dao.TripDao
import com.example.trippocket.data.model.Accommodation
import com.example.trippocket.data.model.AccommodationDocument
import com.example.trippocket.data.model.AccommodationNotification
import com.example.trippocket.data.model.TransportNotification
import com.example.trippocket.data.model.Transport
import com.example.trippocket.data.model.TransportDocument
import com.example.trippocket.data.model.Trip

@Database(
    entities = [
        Trip::class,
        Transport::class,
        TransportNotification::class,
        AccommodationNotification::class,
        Accommodation::class,
        TransportDocument::class,
        AccommodationDocument::class,
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
    abstract fun transportNotificationDao(): TransportNotificationDao
    abstract fun accommodationNotificationDao(): AccommodationNotificationDao
    abstract fun accommodationDao(): AccommodationDao
    abstract fun transportDocumentDao(): TransportDocumentDao
    abstract fun accommodationDocumentDao(): AccommodationDocumentDao
}