package com.example.trippocket.data.repository

import com.example.trippocket.data.dao.TransportDocumentDao
import com.example.trippocket.data.model.TransportDocument
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransportDocumentRepository @Inject constructor(
    private val transportDocumentDao: TransportDocumentDao
) {
    fun getForTransport(
        transportId: Long
    ): Flow<List<TransportDocument>> =
        transportDocumentDao.getForTransport(transportId)

    suspend fun addDocument(
        document: TransportDocument
    ): Long =
        transportDocumentDao.insertDocument(document)

    suspend fun deleteDocument(
        id: Long
    ) =
        transportDocumentDao.deleteDocument(id)

    suspend fun deleteForTransport(
        transportId: Long
    ) {
        transportDocumentDao.deleteForTransport(transportId)
    }
}