package com.viktoriia.trippocket.data.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update
import com.viktoriia.trippocket.data.model.Accommodation
import kotlinx.coroutines.flow.Flow

@Dao
interface AccommodationDao {
    @Query("""
        SELECT *
        FROM accommodations
        WHERE tripId = :tripId
        ORDER BY checkIn ASC
    """)
    fun getForTrip(tripId: Long): Flow<List<Accommodation>>

    @Query("""
        SELECT *
        FROM accommodations
        WHERE id = :id
    """)
    fun getById(id: Long): Flow<Accommodation?>

    @Insert
    suspend fun insertAccommodation(
        accommodation: Accommodation
    ): Long

    @Update
    suspend fun updateAccommodation(
        accommodation: Accommodation
    )

    @Query("DELETE FROM accommodations WHERE id = :id")
    suspend fun deleteAccommodation(
        id: Long
    )
}