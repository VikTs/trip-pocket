package com.viktoriia.trippocket.data.model

import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.Index
import androidx.room3.PrimaryKey
import java.time.LocalDateTime

@Entity(
    tableName = "accommodations",
    foreignKeys = [
        ForeignKey(
            entity = Trip::class,
            parentColumns = ["id"],
            childColumns = ["tripId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("tripId")
    ]
)
data class Accommodation(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val tripId: Long,
    val name: String,
    val address: String,
    val contactPhone: String?,
    val checkIn: LocalDateTime,
    val checkOut: LocalDateTime,
)