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

    fun getTripById(tripId: Long): Flow<Trip?> {
        return tripDao.getTripById(tripId)
    }

    suspend fun addTrip(trip: Trip) {
        tripDao.insertTrip(trip)
    }

    suspend fun updateTrip(trip: Trip) {
        tripDao.updateTrip(trip)
    }

    suspend fun deleteTrip(tripId: Long) {
        tripDao.deleteTrip(tripId)
    }
}