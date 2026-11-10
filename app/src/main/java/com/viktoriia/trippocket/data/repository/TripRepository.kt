package com.viktoriia.trippocket.data.repository

import android.util.Log
import com.viktoriia.trippocket.data.dao.AccommodationDao
import com.viktoriia.trippocket.data.dao.AccommodationDocumentDao
import com.viktoriia.trippocket.data.dao.TransportDao
import com.viktoriia.trippocket.data.dao.TransportDocumentDao
import com.viktoriia.trippocket.data.dao.TripDao
import com.viktoriia.trippocket.data.model.Trip
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class TripRepository @Inject constructor(
    private val tripDao: TripDao,
    private val transportDao: TransportDao,
    private val accommodationDao: AccommodationDao,
    private val transportDocumentDao: TransportDocumentDao,
    private val accommodationDocumentDao: AccommodationDocumentDao,
) {
    fun getTrips(): Flow<List<Trip>> {
        return tripDao.getTrips()
    }

    fun getTripById(tripId: Long): Flow<Trip?> {
        return tripDao.getTripById(tripId)
    }

    suspend fun addTrip(trip: Trip) {
        tripDao.insertTrip(trip)
    }

    suspend fun updateTrip(trip: Trip) {
        tripDao.updateTrip(trip)
    }

    suspend fun deleteTrip(tripId: Long) {
        val trip = tripDao.getTripById(tripId).first()

        val transports = transportDao
            .getTripTransports(tripId)
            .first()

        val accommodations = accommodationDao
            .getForTrip(tripId)
            .first()

        val transportDocumentPaths = transports.flatMap { transport ->
            transportDocumentDao.getFilePathsByTransportId(transport.id)
        }

        val accommodationDocumentPaths = accommodations.flatMap { accommodation ->
            accommodationDocumentDao.getFilePathsByAccommodationId(accommodation.id)
        }

        val filePaths = buildList {
            trip?.imagePath?.let(::add)
            addAll(transportDocumentPaths)
            addAll(accommodationDocumentPaths)
        }.distinct()

        tripDao.deleteTrip(tripId)

        withContext(Dispatchers.IO) {
            filePaths.forEach { path ->
                val file = File(path)

                if (file.exists() && !file.delete()) {
                    Log.w("TripRepository", "Failed to delete file: $path")
                }
            }
        }
    }
}