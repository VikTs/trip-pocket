package com.example.trippocket.data.model

import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.Index
import androidx.room3.PrimaryKey

@Entity(
    tableName = "transport_documents",
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
data class TransportDocument(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val transportId: Long,
    val name: String,
    val path: String
)