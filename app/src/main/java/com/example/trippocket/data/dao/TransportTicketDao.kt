package com.example.trippocket.data.dao

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import com.example.trippocket.data.model.TransportTicket
import kotlinx.coroutines.flow.Flow

@Dao
interface TransportTicketDao {
    @Query("""
        SELECT * 
        FROM transport_tickets 
        WHERE tripId = :tripId
        ORDER BY from_time ASC
    """)
    fun getTicketsForTrip(tripId: Long): Flow<List<TransportTicket>>

    @Insert
    suspend fun insertTicket(ticket: TransportTicket)

    @Delete
    suspend fun deleteTicket(ticket: TransportTicket)
}