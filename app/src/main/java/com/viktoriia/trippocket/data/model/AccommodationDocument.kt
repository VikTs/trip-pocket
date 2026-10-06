package com.viktoriia.trippocket.data.model

import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.Index
import androidx.room3.PrimaryKey

@Entity(
    tableName = "accommodation_documents",
    foreignKeys = [
        ForeignKey(
            entity = Accommodation::class,
            parentColumns = ["id"],
            childColumns = ["accommodationId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("accommodationId")
    ]
)
data class AccommodationDocument(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val accommodationId: Long,
    val name: String,
    val path: String
)