package com.example.trippocket.data.database

import androidx.room3.ColumnTypeConverters
import androidx.room3.Database
import androidx.room3.RoomDatabase
import com.example.trippocket.data.converter.LocalDateConverter
import com.example.trippocket.data.dao.TripDao
import com.example.trippocket.data.model.Trip

@Database(
    entities = [Trip::class],
    version = 1
)
@ColumnTypeConverters(LocalDateConverter::class)
abstract class TripDatabase : RoomDatabase() {
    abstract fun tripDao(): TripDao
}