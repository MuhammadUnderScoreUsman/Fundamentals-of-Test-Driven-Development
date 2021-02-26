package com.mohammadosman.unittest.data.datasource

import com.mohammadosman.unittest.domain.Note

const val NOTE_SUCCESS_ID = "1"
const val NOTE_FAILED_ID = "Fake ID"
const val EXCEPTION_ID = "EXE_ID"
const val NOTE_DELETION_FAILED_ID = "DF_ID"
const val ALREADY_EXISTED_ID = "AL_ID"

class FakeNoteDataSource(
    private val notesData: HashMap<String, Note>
) : NoteDataSource {

    override suspend fun insertNote(note: Note): Long {
        if (note.id == NOTE_FAILED_ID) {
            return -1
        } else if (note.id == EXCEPTION_ID) {
            throw Exception("Unknown EXE")
        }
        notesData.put(note.id, note)
        return 1
    }

    override suspend fun deleteNote(note: Note): Int {
        if (note.id == NOTE_SUCCESS_ID) {
            notesData.remove(note.id)
            return 1
        } else if (note.id == NOTE_DELETION_FAILED_ID) {
            return -1
        }
        return 1
    }

    override suspend fun updateNote(pk: String, title: String, content: String): Int {

        val updateNote = Note.createNote(
            id = pk,
            title = title,
            content = content,
            timestamp = "2020"
        )

        return notesData.get(pk)?.let {
            notesData.put(pk, updateNote)
            1
        } ?: -1
    }

    override suspend fun searchNoteById(id: String): Note? {
        return notesData.get(id)
    }

    override suspend fun searchNotes(noteTitle: String): List<Note> {
        val noteList = ArrayList<Note>()
        for (note in notesData.values) {
            if (note.title.contains(noteTitle)) {
                noteList.add(note)
            }
        }
        return noteList
    }

    override suspend fun getAllNote(): List<Note> {
        return ArrayList(notesData.values)
    }

}