package com.example.trippocket.data.repository

import com.example.trippocket.data.dao.NotificationDao
import com.example.trippocket.data.model.Notification
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotificationRepository @Inject constructor(
    private val notificationDao: NotificationDao
) {
    fun getByTransportId(
        transportId: Long
    ): Flow<Notification?> =
        notificationDao.getByTransportId(transportId)

    suspend fun addNotification(notification: Notification): Long {
        return notificationDao.insertNotification(notification)
    }

    suspend fun updateNotification(notification: Notification) {
        notificationDao.updateNotification(notification)
    }

    suspend fun deleteNotification(id: Long) {
        notificationDao.deleteNotification(id)
    }
}