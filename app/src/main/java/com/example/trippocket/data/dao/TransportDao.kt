package com.example.trippocket.data.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update
import com.example.trippocket.data.model.Transport
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

    @Insert
    suspend fun insertTransport(transport: Transport)

    @Update
    suspend fun updateTransport(transport: Transport)

    @Query("DELETE FROM transports WHERE id = :id")
    suspend fun deleteTransport(id: Long)
}