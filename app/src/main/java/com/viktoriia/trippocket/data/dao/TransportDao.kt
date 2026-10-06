package com.viktoriia.trippocket.data.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update
import com.viktoriia.trippocket.data.model.Transport
import kotlinx.coroutines.flow.Flow

@Dao
interface TransportDao {
    @Query("""
        SELECT * 
        FROM transports
        WHERE tripId = :tripId
        ORDER BY from_time ASC
    """)
    fun getTripTransports(tripId: Long): Flow<List<Transport>>

    @Query("SELECT * FROM transports WHERE id = :id")
    fun getById(
        id: Long
    ): Flow<Transport?>

    @Insert
    suspend fun insertTransport(transport: Transport): Long

    @Update
    suspend fun updateTransport(transport: Transport)

    @Query("DELETE FROM transports WHERE id = :id")
    suspend fun deleteTransport(id: Long)
}