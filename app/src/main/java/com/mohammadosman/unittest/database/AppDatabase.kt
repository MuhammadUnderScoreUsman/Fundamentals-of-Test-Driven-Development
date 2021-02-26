package com.mohammadosman.unittest.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mohammadosman.unittest.database.model.NoteDto

@Database(entities = [NoteDto::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        private val DB_NAME = "note.db"

        @Volatile
        private var instance: AppDatabase? = null

        operator fun invoke(app: Application) = synchronized(this){
            instance ?: initDb(app).also {
                instance = it
            }
        }

        private fun initDb(ctx: Application) = Room.databaseBuilder(
            ctx,
            AppDatabase::class.java,
            DB_NAME
        ).build()

    }

}