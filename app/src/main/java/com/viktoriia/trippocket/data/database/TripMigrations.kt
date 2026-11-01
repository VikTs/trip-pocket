package com.viktoriia.trippocket.data.database

import androidx.room3.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL

val MIGRATION_1_2 = object : Migration(1, 2) {
    override suspend fun migrate(connection: SQLiteConnection) {
        connection.execSQL(
            "ALTER TABLE `trips` ADD COLUMN `imagePath` TEXT"
        )
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override suspend fun migrate(connection: SQLiteConnection) {
        connection.execSQL(
            "ALTER TABLE transports ADD COLUMN from_terminal TEXT"
        )
        connection.execSQL(
            "ALTER TABLE transports ADD COLUMN to_terminal TEXT"
        )
    }
}