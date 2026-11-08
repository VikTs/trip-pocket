package com.viktoriia.trippocket.data.repository

import com.viktoriia.trippocket.data.dao.TransportDao
import com.viktoriia.trippocket.data.model.Transport
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class TransportRepository @Inject constructor(
    private val transportDao: TransportDao,
    private val transportDocumentRepository: TransportDocumentRepository
){
    fun getTripTransports(tripId: Long): Flow<List<Transport>> {
        return transportDao.getTripTransports(tripId)
    }

    fun getById(
        id: Long
    ): Flow<Transport?> {
        return transportDao.getById(id)
    }

    suspend fun addTransport(transport: Transport): Long {
        return transportDao.insertTransport(transport)
    }

    suspend fun updateTransport(transport: Transport) {
        transportDao.updateTransport(transport)
    }

    suspend fun deleteTransport(id: Long) {
        val filePaths = transportDocumentRepository
            .getFilePathsByTransportId(id)

        transportDao.deleteTransport(id)

        withContext(Dispatchers.IO) {
            filePaths.forEach { path ->
                File(path).delete()
            }
        }
    }
}