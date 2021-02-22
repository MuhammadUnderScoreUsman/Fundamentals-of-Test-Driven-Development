package com.mohammadosman.unittest.database.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.mohammadosman.unittest.data.datasource.NoteDataSource
import com.mohammadosman.unittest.database.AppDatabase
import com.mohammadosman.unittest.database.NoteDao
import com.mohammadosman.unittest.database.mapper.NoteMapper
import com.mohammadosman.unittest.domain.Note
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4ClassRunner::class)
class NoteDataSourceImplTest {

    lateinit var db: AppDatabase
    lateinit var dao: NoteDao
    lateinit var noteDataSource: NoteDataSource
    lateinit var noteMapper: NoteMapper

    @Before
    fun setUp() {

        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = db.noteDao()

        noteMapper = NoteMapper()

        noteDataSource = NoteDataSourceImpl(
            dao = dao,
            noteMapper = noteMapper
        )

    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insert_intoDb_checkExist() = runBlocking {

        val newNote = Note.createNote(
            id = UUID.randomUUID().toString(),
            title = UUID.randomUUID().toString(),
            content = UUID.randomUUID().toString(),
            timestamp = UUID.randomUUID().toString()
        )

        noteDataSource.insertNote(newNote)

        val notes = noteDataSource.getAllNote()
        assert(notes.contains(newNote))
    }


    @Test
    fun deleteNote_checkDeletedSuccessFully() = runBlocking {
        val newNote = Note.createNote(
            id = UUID.randomUUID().toString(),
            title = UUID.randomUUID().toString(),
            content = UUID.randomUUID().toString(),
            timestamp = UUID.randomUUID().toString()
        )

        noteDataSource.insertNote(newNote)

        var notes = noteDataSource.getAllNote()

        assert(notes.contains(newNote))

        noteDataSource.deleteNote(newNote)

        // this is to refresh the list
        notes = noteDataSource.getAllNote()

        assert(!notes.contains(newNote))

    }

    @Test
    fun updateNotes_checkUpdates() = runBlocking {

        val newNote = Note.createNote(
            id = UUID.randomUUID().toString(),
            title = UUID.randomUUID().toString(),
            content = UUID.randomUUID().toString(),
            timestamp = UUID.randomUUID().toString()
        )

        noteDataSource.insertNote(newNote)

        noteDataSource.updateNote(
            pk = newNote.id,
            title = "TITLE",
            content = "CONTENT"
        )

        val getNote = noteDataSource.searchNoteById(newNote.id)
        assertEquals(
            newNote.id, getNote?.id
        )

        assertEquals(
            "TITLE", getNote?.title
        )
        assertEquals(
            "CONTENT",
            getNote?.content
        )

    }

    @Test
    fun searchNote_viaTitleAndCheck() = runBlocking {

        val query = "Title"

        val newNote = Note.createNote(
            id = UUID.randomUUID().toString(),
            title = "Title",
            content = UUID.randomUUID().toString(),
            timestamp = UUID.randomUUID().toString()
        )

        noteDataSource.insertNote(newNote)

        val checkForNote = noteDataSource.searchNoteById(newNote.id)

        val searchViaTitle = noteDataSource.searchNotes(noteTitle = query)

        var exist = false
        for (note in searchViaTitle) {
            if (note.id == checkForNote?.id) {
                exist = true
                break
            }
        }
        assert(exist)

    }

}