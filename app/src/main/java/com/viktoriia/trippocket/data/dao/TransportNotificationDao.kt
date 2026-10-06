package com.viktoriia.trippocket.data.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update
import com.viktoriia.trippocket.data.model.TransportNotification
import kotlinx.coroutines.flow.Flow

@Dao
interface TransportNotificationDao {
    @Query(
        """
        SELECT * 
        FROM transport_notifications
        WHERE transportId = :transportId
    """
    )
    fun getByTransportId(
        transportId: Long
    ): Flow<TransportNotification?>

    @Insert
    suspend fun insertNotification(notification: TransportNotification): Long

    @Update
    suspend fun updateNotification(notification: TransportNotification)

    @Query("DELETE FROM transport_notifications WHERE id = :id")
    suspend fun deleteNotification(id: Long)
}