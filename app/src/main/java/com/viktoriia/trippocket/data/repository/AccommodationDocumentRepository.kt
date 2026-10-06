package com.viktoriia.trippocket.data.repository

import com.viktoriia.trippocket.data.dao.AccommodationDocumentDao
import com.viktoriia.trippocket.data.model.AccommodationDocument
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccommodationDocumentRepository @Inject constructor(
    private val accommodationDocumentDao: AccommodationDocumentDao
) {
    fun getForAccommodation(
        accommodationId: Long
    ): Flow<List<AccommodationDocument>> =
        accommodationDocumentDao.getForAccommodation(accommodationId)

    suspend fun addDocument(
        document: AccommodationDocument
    ): Long =
        accommodationDocumentDao.insertDocument(document)

    suspend fun deleteDocument(
        id: Long
    ) =
        accommodationDocumentDao.deleteDocument(id)

    suspend fun deleteForAccommodation(
        accommodationId: Long
    ) {
        accommodationDocumentDao.deleteForAccommodation(accommodationId)
    }
}