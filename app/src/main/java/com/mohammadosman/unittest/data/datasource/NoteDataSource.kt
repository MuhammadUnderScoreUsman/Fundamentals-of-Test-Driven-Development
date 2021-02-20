package com.mohammadosman.unittest.data.datasource

import com.mohammadosman.unittest.model.Note

interface NoteDataSource {

    suspend fun insertNote(note: Note): Long

    suspend fun deleteNote(note: Note): Int

    suspend fun updateNote(pk: String, title: String, content: String): Int

    suspend fun searchNoteById(id: String): Note?

    suspend fun searchNotes(noteTitle: String): List<Note>

    suspend fun getAllNote(): List<Note>

}