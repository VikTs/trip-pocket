package com.example.trippocket.data.model

import androidx.room3.Embedded
import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.PrimaryKey
import java.time.LocalDateTime

@Entity(
    tableName = "transport_tickets",
    foreignKeys = [
        ForeignKey(
            entity = Trip::class,
            parentColumns = ["id"],
            childColumns = ["tripId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TransportTicket(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val tripId: Long,
    val type: TransportType,

    @Embedded(prefix = "from_")
    val from: TransportStop,

    @Embedded(prefix = "to_")
    val to: TransportStop
)

enum class TransportType {
    BUS,
    TRAIN
}

data class TransportStop(
    val city: String,
    val time: LocalDateTime,
)