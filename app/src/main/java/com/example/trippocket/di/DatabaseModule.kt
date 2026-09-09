package com.example.trippocket.di

import android.content.Context
import androidx.room3.Room
import androidx.sqlite.driver.AndroidSQLiteDriver
import com.example.trippocket.data.dao.TransportTicketDao
import com.example.trippocket.data.dao.TripDao
import com.example.trippocket.data.database.TripDatabase
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
    fun provideTransportTicketDao(
        database: TripDatabase
    ): TransportTicketDao {
        return database.transportTicketDao()
    }
}