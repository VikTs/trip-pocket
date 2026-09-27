package com.example.trippocket.data.repository

import com.example.trippocket.data.dao.AccommodationDao
import com.example.trippocket.data.model.Accommodation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccommodationRepository @Inject constructor(
    private val accommodationDao: AccommodationDao
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

    suspend fun deleteAccommodation(
        id: Long
    ) {
        accommodationDao.deleteAccommodation(
            id
        )
    }
}