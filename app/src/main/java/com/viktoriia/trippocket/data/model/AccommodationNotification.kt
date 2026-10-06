package com.viktoriia.trippocket.data.model

import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.Index
import androidx.room3.PrimaryKey

@Entity(
    tableName = "accommodation_notifications",
    foreignKeys = [
        ForeignKey(
            entity = Accommodation::class,
            parentColumns = ["id"],
            childColumns = ["accommodationId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(
            value = ["accommodationId", "type"],
            unique = true
        )
    ]
)
data class AccommodationNotification(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val accommodationId: Long,
    val type: AccommodationNotificationType,
    val enabled: Boolean = true,
    val minutesBefore: Long = 60
)

enum class AccommodationNotificationType {
    CHECK_IN,
    CHECK_OUT
}