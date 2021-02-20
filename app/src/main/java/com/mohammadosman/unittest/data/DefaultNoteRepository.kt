package com.mohammadosman.unittest.data

import com.mohammadosman.unittest.data.datasource.NoteDataSource
import com.mohammadosman.unittest.model.Note
import com.mohammadosman.unittest.util.Resource

class DefaultNoteRepository(
    private val noteDataSource: NoteDataSource
) {

    suspend fun insertNote(note: Note): Resource<Note> {
        val checkForExistence = noteDataSource.searchNoteById(note.id)
        return if (note.title == checkForExistence?.title) {
            Resource.Error(ALREADY_EXISTED)
        } else {
            try {
                val newNote = noteDataSource.insertNote(note)
                if (newNote > 0) {
                    Resource.Success(null, SUCCESS)
                } else {
                    Resource.Error(FAILED)
                }
            } catch (e: Exception) {
                throw e.fillInStackTrace()
            }
        }
    }


    suspend fun deleteNote(note: Note): Resource<Note> {
        val deleteNote = noteDataSource.deleteNote(note)
        if (deleteNote > 0) {
            return Resource.Success(null, DELETED_SUCCESSFULLY)
        } else {
            return Resource.Error(FAILED_DELETION)

        }
    }


    suspend fun updateNote(note: Note): Resource<Note> {

        val updateNote = noteDataSource.updateNote(note.id, note.title, note.content)
        if (updateNote > 0) {
            return Resource.Success(null, UPDATED)
        } else {
            return Resource.Error(ERROR_UPDATION)
        }

    }

    suspend fun noteList(title: String): Resource<List<Note>> {
        if (title.length < QUERYSIZE) {
            val searchNotes = noteDataSource.searchNotes(title)
            return Resource.Success(searchNotes, SEARCH_NOTE)
        } else {
            return Resource.Error(SEARCH_FAILED)
        }
    }

    companion object {

        // Insertion
        const val SUCCESS = "Added New Note"
        const val FAILED = "Failed to Insert."
        const val ALREADY_EXISTED = "Note Already Existed"

        // Deletion
        const val DELETED_SUCCESSFULLY = "Deleted!!!!"
        const val FAILED_DELETION = "FAILED_DELETION"

        // Update
        const val UPDATED = "Updated"
        const val ERROR_UPDATION = "Not Updated"

        // Search
        const val SEARCH_NOTE = "Searched Successfully"
        const val SEARCH_FAILED = "Search failed"
        const val QUERYSIZE = 15
    }


}