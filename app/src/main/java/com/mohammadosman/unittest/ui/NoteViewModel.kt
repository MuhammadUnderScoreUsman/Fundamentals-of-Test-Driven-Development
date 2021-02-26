package com.mohammadosman.unittest.ui

import androidx.lifecycle.ViewModel
import com.mohammadosman.unittest.data.DefaultNoteRepository
import com.mohammadosman.unittest.domain.Note
import com.mohammadosman.unittest.util.Resource

class NoteViewModel(
    private val defaultNoteRepository: DefaultNoteRepository
) : ViewModel() {

    suspend fun insertNote(insetNote: Note): Resource<Note> {
        return defaultNoteRepository.insertNote(insetNote)
    }

    suspend fun deleteNote(note: Note): Resource<Note> {
        return defaultNoteRepository.deleteNote(note)
    }

    suspend fun updateNote(note: Note): Resource<Note> {
        return defaultNoteRepository.updateNote(note)
    }

    suspend fun searchNotes(title: String): Resource<List<Note>> {
        return defaultNoteRepository.noteList(title)
    }


}