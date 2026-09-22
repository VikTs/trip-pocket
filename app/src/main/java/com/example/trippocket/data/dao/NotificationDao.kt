package com.example.trippocket.data.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update
import com.example.trippocket.data.model.Notification
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {
    @Query(
        """
        SELECT * 
        FROM notifications
        WHERE transportId = :transportId
    """
    )
    fun getByTransportId(
        transportId: Long
    ): Flow<Notification?>

    @Insert
    suspend fun insertNotification(notification: Notification): Long

    @Update
    suspend fun updateNotification(notification: Notification)

    @Query("DELETE FROM notifications WHERE id = :id")
    suspend fun deleteNotification(id: Long)
}