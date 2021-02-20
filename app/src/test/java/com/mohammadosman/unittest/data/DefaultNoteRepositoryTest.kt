package com.mohammadosman.unittest.data

import com.mohammadosman.unittest.DContainer
import com.mohammadosman.unittest.data.DefaultNoteRepository.Companion.ALREADY_EXISTED
import com.mohammadosman.unittest.data.DefaultNoteRepository.Companion.DELETED_SUCCESSFULLY
import com.mohammadosman.unittest.data.DefaultNoteRepository.Companion.ERROR_UPDATION
import com.mohammadosman.unittest.data.DefaultNoteRepository.Companion.FAILED
import com.mohammadosman.unittest.data.DefaultNoteRepository.Companion.SEARCH_FAILED
import com.mohammadosman.unittest.data.DefaultNoteRepository.Companion.SEARCH_NOTE
import com.mohammadosman.unittest.data.DefaultNoteRepository.Companion.SUCCESS
import com.mohammadosman.unittest.data.DefaultNoteRepository.Companion.UPDATED
import com.mohammadosman.unittest.data.datasource.*
import com.mohammadosman.unittest.model.Note
import com.mohammadosman.unittest.model.Note.NoteFactory
import com.mohammadosman.unittest.util.Resource
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.collections.ArrayList

@InternalCoroutinesApi
class DefaultNoteRepositoryTest {

    lateinit var noteDataSource: NoteDataSource

    lateinit var defaultNoteRepository: DefaultNoteRepository

    lateinit var dContainer: DContainer


    init {

        dContainer = DContainer()

        dContainer.build()

        noteDataSource = dContainer.noteDataSource

        defaultNoteRepository = DefaultNoteRepository(
            noteDataSource
        )

    }

    @Test
    fun insertNote_withSuccessMessage_returnTrue() = runBlocking {

        val newNote = Note.createNote(
            NOTE_SUCCESS_ID,
            title = "Way title",
            content = "This is content",
            timestamp = "2020"
        )

        val insertNote = defaultNoteRepository.insertNote(newNote)

        assertEquals(Resource.Success(null, SUCCESS), insertNote)


        val checkInCache = noteDataSource.searchNoteById(newNote.id)

        assertTrue {
            checkInCache == newNote
        }

    }

    @Test
    fun insertionError_checkNoteIsNotInserted_cacheIsStillSame() = runBlocking {
        val newNote = NoteFactory.createNote(
            NOTE_FAILED_ID,
            title = "This is note's title",
            content = "Note content is nothing.",
            timestamp = "2021"
        )

        val failedInsertion = defaultNoteRepository.insertNote(newNote)

        assertEquals(Resource.Error(FAILED), failedInsertion)

        val checkInCache = noteDataSource.searchNoteById(newNote.id)

        assertTrue {
            checkInCache != newNote
        }
    }

    @Test
    fun insertNote_throwException_confirmNoteIsNotInTheCache() = runBlocking {

        val newNote = NoteFactory.createNote(
            id = EXCEPTION_ID,
            title = "This is note's title",
            content = "Note content is nothing.",
            timestamp = "2021"
        )

        try {
            defaultNoteRepository.insertNote(newNote)
        } catch (
            e: Exception
        ) {
            e.printStackTrace()
        }

        /*kotlin.runCatching {
            defaultNoteRepository.insertNote(newNote)
        }.onFailure {
            assertEquals(Exception("Unknown EXE"), it.fillInStackTrace())
        }*/

        val checkInCache = noteDataSource.searchNoteById(newNote.id)

        assertTrue {
            checkInCache != newNote
        }
    }

    @Test
    fun insertionFailed_becauseOfAlreadyExisted() = runBlocking {

        val newNote = NoteFactory.createNote(
            NOTE_SUCCESS_ID,
            title = "This is note's title",
            content = "Note content is nothing.",
            timestamp = "2021"
        )


        defaultNoteRepository.insertNote(newNote)

        val title2 = defaultNoteRepository.insertNote(newNote)

        assertEquals(Resource.Error(ALREADY_EXISTED), title2)

        val checkForExistence = noteDataSource.searchNoteById(
            newNote.id
        )

        assertTrue {
            checkForExistence == newNote
        }
    }

    @Test
    fun deleteNote_successMessageReturn_confirmNoLongerInCache() = runBlocking {

        val newNote = NoteFactory.createNote(
            NOTE_SUCCESS_ID,
            title = "This is note's title",
            content = "Note content is nothing.",
            timestamp = "2021"
        )

        defaultNoteRepository.insertNote(newNote)

        val deleteNote = defaultNoteRepository.deleteNote(newNote)


        assertEquals(Resource.Success(null, DELETED_SUCCESSFULLY), deleteNote)


        val checkInCache = noteDataSource.searchNoteById(newNote.id)

        assertTrue {
            checkInCache != newNote
        }

    }

    @Test
    fun deleteNote_failedDeletion_confirmNoteIsStillInCache() = runBlocking {


        val newNote = NoteFactory.createNote(
            NOTE_DELETION_FAILED_ID,
            title = "This is note's title",
            content = "Note content is nothing.",
            timestamp = "2021"
        )

        defaultNoteRepository.insertNote(newNote)

        val deleteNote = defaultNoteRepository.deleteNote(newNote)

        assertEquals(Resource.Error(DefaultNoteRepository.FAILED_DELETION), deleteNote)


        val checkInCache = noteDataSource.searchNoteById(newNote.id)

        assertTrue {
            checkInCache == newNote
        }

    }


    @Test
    fun updateNote_ConfirmThatSuccessFully_checkFromTheCache() = runBlocking {

        val getNote = noteDataSource.searchNotes("title")[0]


        val updateNote = Note.createNote(
            id = getNote.id,
            title = "title",
            content = "content",
            timestamp = "2020"
        )

        val result =
            defaultNoteRepository.updateNote(updateNote)


        assertEquals(Resource.Success(null, UPDATED), result)


        val checkForUpdate = noteDataSource.searchNoteById(updateNote.id)


        assertTrue {
            checkForUpdate == updateNote
        }

    }

    @Test
    fun updateNote_failedToInsert_checkCacheNotUpdate() = runBlocking {

        val updateNote = Note.createNote(
            id = UUID.randomUUID().toString(),
            title = UUID.randomUUID().toString(),
            content = UUID.randomUUID().toString(),
            timestamp = UUID.randomUUID().toString()
        )

        val result =
            defaultNoteRepository.updateNote(updateNote)


        assertEquals(Resource.Error(ERROR_UPDATION), result)


        val checkForUpdate = noteDataSource.searchNoteById(updateNote.id)

        assertTrue {
            checkForUpdate != updateNote
        }

    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    @Test
    fun queryNote_confirmItWasRetreive() = runBlocking {

       val query = "title"

        var result: ArrayList<Note>? = null

        val searchNotes = defaultNoteRepository.noteList(query)

        when (searchNotes) {
            is Resource.Success -> {
                result = ArrayList(searchNotes.data)
            }
        }

        assertEquals(Resource.Success(result, SEARCH_NOTE), searchNotes)

        assertTrue { result != null }

        val noteInCache = noteDataSource.searchNotes(query)

        assertTrue {
            result?.containsAll(noteInCache) ?: false
        }
    }

    @Test
    fun searchQuery_withRandomTitle_returnNothing() = runBlocking {
        val query = "ajskdhfxcvbnzxmcvb,asjdajskdhfxcvbnzxmcvb,asjdajskdhfxcvbnzxmcvb,asjdajskdhfxcvbnzxmcvb,asjdajskdhfxcvbnzxmcvb,asjd"

        var result: ArrayList<Note>? = null

        val searchNotes = defaultNoteRepository.noteList(query)

        when (searchNotes) {
            is Resource.Success -> {
                result = ArrayList(searchNotes.data)
            }
        }

        assertEquals(Resource.Error(SEARCH_FAILED), searchNotes)

        assertTrue { result == null }

        val noteInCache = noteDataSource.searchNotes(query)

        assertFalse {
            result?.containsAll(noteInCache) ?: false
        }
    }

}
