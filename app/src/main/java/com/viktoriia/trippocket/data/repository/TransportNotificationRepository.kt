package com.viktoriia.trippocket.data.repository

import com.viktoriia.trippocket.data.dao.TransportNotificationDao
import com.viktoriia.trippocket.data.model.TransportNotification
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
}