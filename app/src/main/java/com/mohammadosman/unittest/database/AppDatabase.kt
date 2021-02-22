package com.mohammadosman.unittest.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mohammadosman.unittest.database.model.NoteDto

@Database(entities = [NoteDto::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

}