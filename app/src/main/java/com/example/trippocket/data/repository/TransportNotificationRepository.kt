package com.example.trippocket.data.repository

import com.example.trippocket.data.dao.TransportNotificationDao
import com.example.trippocket.data.model.TransportNotification
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransportNotificationRepository @Inject constructor(
    private val notificationDao: TransportNotificationDao
) {
    fun getByTransportId(
        transportId: Long
    ): Flow<TransportNotification?> =
        notificationDao.getByTransportId(transportId)

    suspend fun addNotification(notification: TransportNotification): Long {
        return notificationDao.insertNotification(notification)
    }

    suspend fun updateNotification(notification: TransportNotification) {
        notificationDao.updateNotification(notification)
    }

    suspend fun deleteNotification(id: Long) {
        notificationDao.deleteNotification(id)
    }

    suspend fun setEnabled(transportId: Long, enabled: Boolean) {
        notificationDao.setEnabled(transportId, enabled)
    }
}