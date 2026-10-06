package com.viktoriia.trippocket.di

import android.content.Context
import androidx.room3.Room
import androidx.sqlite.driver.AndroidSQLiteDriver
import com.viktoriia.trippocket.data.dao.AccommodationDao
import com.viktoriia.trippocket.data.dao.AccommodationDocumentDao
import com.viktoriia.trippocket.data.dao.AccommodationNotificationDao
import com.viktoriia.trippocket.data.dao.TransportDao
import com.viktoriia.trippocket.data.dao.TransportDocumentDao
import com.viktoriia.trippocket.data.dao.TransportNotificationDao
import com.viktoriia.trippocket.data.dao.TripDao
import com.viktoriia.trippocket.data.database.TripDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): TripDatabase {
        return Room
            .databaseBuilder<TripDatabase>(
                context = context,
                name = "trip_pocket.db"
            )
            .setDriver(AndroidSQLiteDriver())
            .build()
    }

    @Provides
    fun provideTripDao(
        database: TripDatabase
    ): TripDao {
        return database.tripDao()
    }

    @Provides
    fun provideTransportDao(
        database: TripDatabase
    ): TransportDao {
        return database.transportDao()
    }

    @Provides
    fun provideTransportNotificationDao(
        database: TripDatabase
    ): TransportNotificationDao =
        database.transportNotificationDao()

    @Provides
    fun provideAccommodationNotificationDao(
        database: TripDatabase
    ): AccommodationNotificationDao =
        database.accommodationNotificationDao()

    @Provides
    fun provideAccommodationDao(
        database: TripDatabase
    ): AccommodationDao {
        return database.accommodationDao()
    }

    @Provides
    fun provideTransportDocumentDao(
        database: TripDatabase
    ): TransportDocumentDao = database.transportDocumentDao()

    @Provides
    fun provideAccommodationDocumentDao(
        database: TripDatabase
    ): AccommodationDocumentDao = database.accommodationDocumentDao()
}