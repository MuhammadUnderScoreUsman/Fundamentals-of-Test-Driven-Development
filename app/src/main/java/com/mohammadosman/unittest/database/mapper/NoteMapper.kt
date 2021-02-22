package com.mohammadosman.unittest.database.mapper

import com.mohammadosman.unittest.database.model.NoteDto
import com.mohammadosman.unittest.domain.DomainMapper
import com.mohammadosman.unittest.domain.Note

class NoteMapper : DomainMapper<Note, NoteDto> {
    override fun mapToDomainModel(t: NoteDto): Note {
        return Note(
            id = t.id,
            title = t.title,
            content = t.content,
            timestamp = t.timestamp
        )
    }

    override fun mapFromDomainModel(domainModel: Note): NoteDto {
        return NoteDto(
            id = domainModel.id,
            title = domainModel.title,
            content = domainModel.content,
            timestamp = domainModel.timestamp
        )
    }

    fun mapToDomainList(list: List<NoteDto>): List<Note> {
        return list.map {
            mapToDomainModel(it)
        }
    }

    fun mapFromDomainList(list: List<Note>): List<NoteDto> {
        return list.map {
            mapFromDomainModel(it)
        }
    }
}