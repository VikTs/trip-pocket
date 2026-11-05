package com.viktoriia.trippocket.di

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
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Provides
    fun provideTripDao(db: TripDatabase): TripDao = db.tripDao()

    @Provides
    fun provideTransportDao(db: TripDatabase): TransportDao = db.transportDao()

    @Provides
    fun provideTransportNotificationDao(db: TripDatabase): TransportNotificationDao =
        db.transportNotificationDao()

    @Provides
    fun provideTransportDocumentDao(db: TripDatabase): TransportDocumentDao =
        db.transportDocumentDao()

    @Provides
    fun provideAccommodationDao(db: TripDatabase): AccommodationDao =
        db.accommodationDao()

    @Provides
    fun provideAccommodationNotificationDao(db: TripDatabase): AccommodationNotificationDao =
        db.accommodationNotificationDao()

    @Provides
    fun provideAccommodationDocumentDao(db: TripDatabase): AccommodationDocumentDao =
        db.accommodationDocumentDao()
}