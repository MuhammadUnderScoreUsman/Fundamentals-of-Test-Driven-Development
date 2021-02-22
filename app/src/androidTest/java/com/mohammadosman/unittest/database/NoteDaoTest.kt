package com.mohammadosman.unittest.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mohammadosman.unittest.database.model.NoteDto
import com.mohammadosman.unittest.domain.Note
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class NoteDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: NoteDao

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = db.noteDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insert_intoDb_checkExist() = runBlocking {

        val newNote = NoteDto(
            id = UUID.randomUUID().toString(),
            title = UUID.randomUUID().toString(),
            content = UUID.randomUUID().toString(),
            timestamp = UUID.randomUUID().toString()
        )

        dao.insertNote(newNote)

        val notes = dao.getAllNotes()
        assert(notes.contains(newNote))
    }




}