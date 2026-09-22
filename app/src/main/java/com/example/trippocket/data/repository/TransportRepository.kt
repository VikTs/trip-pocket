package com.example.trippocket.data.repository

import com.example.trippocket.data.dao.TransportDao
import com.example.trippocket.data.model.Transport
import kotlinx.coroutines.flow.Flow

class TransportRepository(
    private val transportDao: TransportDao
) {
    fun getTripTransports(tripId: Long): Flow<List<Transport>> {
        return transportDao.getTripTransports(tripId)
    }

    suspend fun addTransport(transport: Transport): Long {
        return transportDao.insertTransport(transport)
    }

    suspend fun updateTransport(transport: Transport) {
        transportDao.updateTransport(transport)
    }

    suspend fun deleteTransport(id: Long) {
        transportDao.deleteTransport(id)
    }
}