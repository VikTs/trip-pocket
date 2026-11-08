package com.viktoriia.trippocket.data.repository

import com.viktoriia.trippocket.data.dao.AccommodationDao
import com.viktoriia.trippocket.data.dao.AccommodationDocumentDao
import com.viktoriia.trippocket.data.model.Accommodation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class AccommodationRepository @Inject constructor(
    private val accommodationDao: AccommodationDao,
    private val accommodationDocumentDao: AccommodationDocumentDao,
) {
    fun getForTrip(
        tripId: Long
    ): Flow<List<Accommodation>> =
        accommodationDao.getForTrip(tripId)

    fun getById(
        id: Long
    ): Flow<Accommodation?> =
        accommodationDao.getById(id)

    suspend fun addAccommodation(
        accommodation: Accommodation
    ): Long {
        return accommodationDao.insertAccommodation(
            accommodation
        )
    }

    suspend fun updateAccommodation(
        accommodation: Accommodation
    ) {
        accommodationDao.updateAccommodation(
            accommodation
        )
    }

    suspend fun deleteAccommodation(id: Long) {
        val filePaths = accommodationDocumentDao
            .getFilePathsByAccommodationId(id)

        accommodationDao.deleteAccommodation(id)

        withContext(Dispatchers.IO) {
            filePaths.forEach { path ->
                File(path).delete()
            }
        }
    }
}