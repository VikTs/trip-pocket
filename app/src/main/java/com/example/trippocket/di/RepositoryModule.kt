package com.example.trippocket.di

import com.example.trippocket.data.dao.TransportDao
import com.example.trippocket.data.dao.TripDao
import com.example.trippocket.data.repository.TransportRepository
import com.example.trippocket.data.repository.TripRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideTripRepository(
        tripDao: TripDao
    ): TripRepository {
        return TripRepository(tripDao)
    }

    @Provides
    fun provideTransportRepository(
        transportDao: TransportDao
    ): TransportRepository {
        return TransportRepository(transportDao)
    }
}