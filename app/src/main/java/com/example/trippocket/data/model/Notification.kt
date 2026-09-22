package com.example.trippocket.data.model

import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.Index
import androidx.room3.PrimaryKey

@Entity(
    tableName = "notifications",
    foreignKeys = [
        ForeignKey(
            entity = Transport::class,
            parentColumns = ["id"],
            childColumns = ["transportId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("transportId")
    ]
)
data class Notification(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val transportId: Long,
    val enabled: Boolean = true,
    val minutesBefore: Long = 60
)