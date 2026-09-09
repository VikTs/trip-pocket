package com.example.trippocket.data.dao

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import com.example.trippocket.data.model.Trip
import kotlinx.coroutines.flow.Flow

@Dao
interface TripDao {
    @Query("SELECT * FROM trips ORDER BY startDate ASC")
    fun getTrips(): Flow<List<Trip>>

    @Insert
    suspend fun insertTrip(trip: Trip)

    @Delete
    suspend fun deleteTrip(trip: Trip)
}