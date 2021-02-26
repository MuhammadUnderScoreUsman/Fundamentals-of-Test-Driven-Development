package com.mohammadosman.unittest.ui

import com.mohammadosman.unittest.data.DefaultNoteRepository
import com.mohammadosman.unittest.domain.Note
import com.mohammadosman.unittest.domain.Note.NoteFactory
import com.mohammadosman.unittest.util.Resource
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.junit.jupiter.MockitoSettings
import org.mockito.quality.Strictness

// This could've been tested using test fakes but i used Mockito for demonstration.
// Mockito with Junit5

@ExtendWith(MockitoExtension::class)
@MockitoSettings(strictness = Strictness.LENIENT)
class NoteViewModelTest {

    private lateinit var noteViewModel: NoteViewModel

    @Mock
    lateinit var noteDefaultRepository: DefaultNoteRepository


    @BeforeEach
    fun setup() {

        MockitoAnnotations.initMocks(this)

        noteViewModel = NoteViewModel(
            noteDefaultRepository
        )
    }

    @Test
    fun insertNote_verifyFromRepo() = runBlocking {

        val newNote: Note = NoteFactory.createNote(
            "FAKED_ID",
            "title is here",
            "content",
            "2021-2021"
        )


        `when`(noteDefaultRepository.insertNote(newNote)).thenReturn(
            Resource.Success(
                null,
                DefaultNoteRepository.SUCCESS
            )
        )

        val value = noteViewModel.insertNote(newNote)

        verify(noteDefaultRepository).insertNote(newNote)



        assertEquals(
            Resource.Success(
                null,
                DefaultNoteRepository.SUCCESS
            ), value
        )

    }

    @Test
    fun deleteNote_verifyFromRepo() = runBlocking {

        val newNote: Note = NoteFactory.createNote(
            "FAKED_ID",
            "title is here",
            "content",
            "2021-2021"
        )

        `when`(noteDefaultRepository.deleteNote(newNote)).thenReturn(
            Resource.Success(
                null,
                DefaultNoteRepository.DELETED_SUCCESSFULLY
            )
        )

        noteViewModel.insertNote(newNote)
        val deleteNote = noteViewModel.deleteNote(newNote)

        verify(noteDefaultRepository).deleteNote(newNote)

        assertEquals(
            Resource.Success(
                null,
                DefaultNoteRepository.DELETED_SUCCESSFULLY
            ),
            deleteNote
        )

    }

    @Test
    fun updateNote_verifyFromRepo() = runBlocking {

        val newNote: Note = NoteFactory.createNote(
            "FAKED_ID",
            "title is here",
            "content",
            "2021-2021"
        )

        val updatedNote: Note = NoteFactory.createNote(
            "1274-112",
            "New title",
            "New content",
            "New - 2021-2021",
        )

        `when`(noteDefaultRepository.updateNote(updatedNote)).thenReturn(
            Resource.Success(
                null,
                DefaultNoteRepository.UPDATED
            )
        )

        noteViewModel.insertNote(newNote)

        val udpatedNote = noteViewModel.updateNote(updatedNote)

        verify(noteDefaultRepository).updateNote(updatedNote)

        assertEquals(
            Resource.Success(
                null,
                DefaultNoteRepository.UPDATED
            ),
            udpatedNote
        )

    }

    @Test
    fun searchNote_verifyFromRepo() = runBlocking {
        val newNote: Note = NoteFactory.createNote(
            "FAKED_ID",
            "title is here",
            "content",
            "2021-2021"
        )

        `when`(noteDefaultRepository.noteList(newNote.title)).thenReturn(
            Resource.Success(
                null,
                DefaultNoteRepository.SEARCH_NOTE
            )
        )

        noteViewModel.insertNote(newNote)

        val searchNote = noteViewModel.searchNotes(newNote.title)

        verify(noteDefaultRepository).noteList(newNote.title)

        assertEquals(
            Resource.Success(
                null,
                DefaultNoteRepository.SEARCH_NOTE
            ),
            searchNote
        )

    }

}