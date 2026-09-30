package com.example.trippocket.data.repository

import com.example.trippocket.data.dao.AccommodationNotificationDao
import com.example.trippocket.data.model.AccommodationNotification
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccommodationNotificationRepository @Inject constructor(
    private val notificationDao: AccommodationNotificationDao
) {
    fun getByAccommodationId(
        accommodationId: Long
    ): Flow<AccommodationNotification?> =
        notificationDao.getByAccommodationId(accommodationId)

    suspend fun addNotification(notification: AccommodationNotification): Long {
        return notificationDao.insertNotification(notification)
    }

    suspend fun updateNotification(notification: AccommodationNotification) {
        notificationDao.updateNotification(notification)
    }

    suspend fun deleteNotification(id: Long) {
        notificationDao.deleteNotification(id)
    }

    suspend fun setEnabled(accommodationId: Long, enabled: Boolean) {
        notificationDao.setEnabled(accommodationId, enabled)
    }
}