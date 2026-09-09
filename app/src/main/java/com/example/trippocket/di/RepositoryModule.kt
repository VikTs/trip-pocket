package com.example.trippocket.di

import com.example.trippocket.data.dao.TransportTicketDao
import com.example.trippocket.data.dao.TripDao
import com.example.trippocket.data.repository.TransportTicketRepository
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
    fun provideTransportTicketRepository(
        transportTicketDao: TransportTicketDao
    ): TransportTicketRepository {
        return TransportTicketRepository(transportTicketDao)
    }
}