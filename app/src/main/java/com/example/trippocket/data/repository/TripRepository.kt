package com.example.trippocket.data.repository

import com.example.trippocket.data.dao.TripDao
import com.example.trippocket.data.model.Trip
import kotlinx.coroutines.flow.Flow

class TripRepository(
    private val tripDao: TripDao
) {
    fun getTrips(): Flow<List<Trip>> {
        return tripDao.getTrips()
    }

    suspend fun addTrip(trip: Trip) {
        tripDao.insertTrip(trip)
    }

    suspend fun deleteTrip(trip: Trip) {
        tripDao.deleteTrip(trip)
    }
}