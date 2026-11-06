package com.viktoriia.trippocket.data.repository

import android.content.Context
import android.util.Log
import com.viktoriia.trippocket.data.model.Airport
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AirportRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val json = Json { ignoreUnknownKeys = true }
    private val mutex = Mutex()

    @Volatile
    private var cache: List<Airport>? = null

    private suspend fun load(): List<Airport> {
        cache?.let { return it }

        return mutex.withLock {
            cache ?: withContext(Dispatchers.IO) {
                try {
                    context.assets.open("airports.json").use {
                        json.decodeFromStream<List<Airport>>(it)
                    }.also { cache = it }
                } catch (e: IOException) {
                    Log.w("AirportRepository", "airports.json not found or unreadable", e)
                    emptyList()
                } catch (e: SerializationException) {
                    Log.w("AirportRepository", "airports.json has invalid format", e)
                    emptyList()
                }
            }
        }
    }

    suspend fun getAll(): List<Airport> = load()

    suspend fun search(query: String): List<Airport> {
        val all = load()
        if (query.isBlank()) return all
        return withContext(Dispatchers.Default) {
            all.filter {
                it.iataCode.startsWith(query, ignoreCase = true) ||
                        it.name.contains(query, ignoreCase = true)
            }
        }
    }
}