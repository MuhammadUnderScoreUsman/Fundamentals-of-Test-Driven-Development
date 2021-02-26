package com.mohammadosman.unittest.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.mohammadosman.unittest.data.DefaultNoteRepository
import com.mohammadosman.unittest.data.datasource.NoteDataSource
import com.mohammadosman.unittest.database.AppDatabase
import com.mohammadosman.unittest.database.NoteDao
import com.mohammadosman.unittest.database.data.NoteDataSourceImpl
import com.mohammadosman.unittest.database.mapper.NoteMapper

class BaseApplication : Application() {
    private lateinit var noteDataSource: NoteDataSource
    private lateinit var defaultNoteRepository: DefaultNoteRepository
    private lateinit var noteDao: NoteDao
    private lateinit var db: AppDatabase
    lateinit var noteViewModelFactory: ViewModelProvider.Factory

    override fun onCreate() {
        super.onCreate()

        db = AppDatabase.invoke(this)

        noteDao = db.noteDao()

        noteDataSource = NoteDataSourceImpl(
            noteDao,
            NoteMapper()
        )

        defaultNoteRepository = DefaultNoteRepository(
            noteDataSource = noteDataSource
        )

        noteViewModelFactory = NoteViewModelFactory(
            defaultNoteRepository = defaultNoteRepository
        )

    }


}