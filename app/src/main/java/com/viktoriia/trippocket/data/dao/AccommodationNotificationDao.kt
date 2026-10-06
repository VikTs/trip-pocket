package com.viktoriia.trippocket.data.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update
import com.viktoriia.trippocket.data.model.AccommodationNotification
import kotlinx.coroutines.flow.Flow

@Dao
interface AccommodationNotificationDao {
    @Query(
        """
        SELECT * 
        FROM accommodation_notifications
        WHERE accommodationId = :accommodationId
    """
    )
    fun getByAccommodationId(
        accommodationId: Long
    ): Flow<List<AccommodationNotification>?>

    @Insert
    suspend fun insertNotification(notification: AccommodationNotification): Long

    @Update
    suspend fun updateNotification(notification: AccommodationNotification)

    @Query("DELETE FROM accommodation_notifications WHERE id = :id")
    suspend fun deleteNotification(id: Long)
}