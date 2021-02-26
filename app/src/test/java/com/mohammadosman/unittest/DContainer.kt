package com.mohammadosman.unittest

import com.mohammadosman.unittest.data.datasource.FakeNoteDataSource
import com.mohammadosman.unittest.data.datasource.NoteDataSource
import com.mohammadosman.unittest.domain.Note

class DContainer {

    lateinit var noteDataSource: NoteDataSource
    private var noteData : HashMap<String, Note> = HashMap()
    lateinit var noteFactory: NoteFactory


    fun build() {

        this.javaClass.classLoader?.let {
            noteFactory = NoteFactory(it)
        }

        noteData = noteFactory.produceData(
            noteFactory.noteList()
        )

        noteDataSource = FakeNoteDataSource(
            notesData = noteData
        )

    }



}
