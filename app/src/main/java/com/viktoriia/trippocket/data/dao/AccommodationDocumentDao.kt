package com.viktoriia.trippocket.data.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import com.viktoriia.trippocket.data.model.AccommodationDocument
import kotlinx.coroutines.flow.Flow

@Dao
interface AccommodationDocumentDao {
    @Query(
        """
        SELECT *
        FROM accommodation_documents
        WHERE accommodationId = :accommodationId
        ORDER BY id ASC
    """
    )
    fun getForAccommodation(
        accommodationId: Long
    ): Flow<List<AccommodationDocument>>

    @Insert
    suspend fun insertDocument(
        document: AccommodationDocument
    ): Long

    @Query("DELETE FROM accommodation_documents WHERE id = :id")
    suspend fun deleteDocument(
        id: Long
    )

    @Query(
        "DELETE FROM accommodation_documents WHERE accommodationId = :accommodationId"
    )
    suspend fun deleteForAccommodation(
        accommodationId: Long
    )
}