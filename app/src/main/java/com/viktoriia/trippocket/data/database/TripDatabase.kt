package com.viktoriia.trippocket.data.database

import androidx.room3.ColumnTypeConverters
import androidx.room3.Database
import androidx.room3.RoomDatabase
import com.viktoriia.trippocket.data.converter.LocalDateConverter
import com.viktoriia.trippocket.data.converter.TransportTypeConverter
import com.viktoriia.trippocket.data.dao.AccommodationDao
import com.viktoriia.trippocket.data.dao.AccommodationDocumentDao
import com.viktoriia.trippocket.data.dao.AccommodationNotificationDao
import com.viktoriia.trippocket.data.dao.TransportDao
import com.viktoriia.trippocket.data.dao.TransportDocumentDao
import com.viktoriia.trippocket.data.dao.TransportNotificationDao
import com.viktoriia.trippocket.data.dao.TripDao
import com.viktoriia.trippocket.data.model.Accommodation
import com.viktoriia.trippocket.data.model.AccommodationDocument
import com.viktoriia.trippocket.data.model.AccommodationNotification
import com.viktoriia.trippocket.data.model.TransportNotification
import com.viktoriia.trippocket.data.model.Transport
import com.viktoriia.trippocket.data.model.TransportDocument
import com.viktoriia.trippocket.data.model.Trip

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