package com.example.trippocket.data.repository

import com.example.trippocket.data.dao.TransportTicketDao
import com.example.trippocket.data.model.TransportTicket
import kotlinx.coroutines.flow.Flow

class TransportTicketRepository(
    private val transportTicketDao: TransportTicketDao
) {
    fun getTicketsForTrip(tripId: Long): Flow<List<TransportTicket>> {
        return transportTicketDao.getTicketsForTrip(tripId)
    }

    suspend fun addTicket(ticket: TransportTicket) {
        transportTicketDao.insertTicket(ticket)
    }

    suspend fun deleteTicket(ticket: TransportTicket) {
        transportTicketDao.deleteTicket(ticket)
    }
}