package com.viktoriia.trippocket.data.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import com.viktoriia.trippocket.data.model.TransportDocument
import kotlinx.coroutines.flow.Flow

@Dao
interface TransportDocumentDao {
    @Query(
        """
        SELECT *
        FROM transport_documents
        WHERE transportId = :transportId
        ORDER BY id ASC
    """
    )
    fun getForTransport(
        transportId: Long
    ): Flow<List<TransportDocument>>

    @Insert
    suspend fun insertDocument(
        document: TransportDocument
    ): Long

    @Query("DELETE FROM transport_documents WHERE id = :id")
    suspend fun deleteDocument(
        id: Long
    )

    @Query(
        "DELETE FROM transport_documents WHERE transportId = :transportId"
    )
    suspend fun deleteForTransport(
        transportId: Long
    )
}