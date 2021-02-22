package com.mohammadosman.unittest.database.data

import com.mohammadosman.unittest.data.datasource.NoteDataSource
import com.mohammadosman.unittest.database.NoteDao
import com.mohammadosman.unittest.database.mapper.NoteMapper
import com.mohammadosman.unittest.domain.Note

class NoteDataSourceImpl(
    private val dao: NoteDao,
    private val noteMapper: NoteMapper
) : NoteDataSource {

    override suspend fun insertNote(note: Note): Long {
        return dao.insertNote(noteMapper.mapFromDomainModel(note))
    }

    override suspend fun deleteNote(note: Note): Int {
        return dao.deleteNote(noteMapper.mapFromDomainModel(note))
    }

    override suspend fun updateNote(pk: String, title: String, content: String): Int {
        return dao.updateNote(
            id = pk,
            title = title,
            content = content
        )
    }

    override suspend fun searchNoteById(id: String): Note? {
        return dao.getNoteViaId(id = id)?.let {
            noteMapper.mapToDomainModel(it)
        }
    }

    override suspend fun searchNotes(noteTitle: String): List<Note> {
        val searchNote = dao.searchNote(queryTitle = noteTitle)
        return noteMapper.mapToDomainList(searchNote)
    }

    override suspend fun getAllNote(): List<Note> {
        return noteMapper.mapToDomainList(dao.getAllNotes())
    }
}