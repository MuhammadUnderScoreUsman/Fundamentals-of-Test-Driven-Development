package com.mohammadosman.unittest.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mohammadosman.unittest.data.DefaultNoteRepository

class NoteViewModelFactory(
    private val  defaultNoteRepository: DefaultNoteRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            NoteViewModel(defaultNoteRepository) as T
        } else {
            throw IllegalAccessException("Wrong model Class")
        }
    }

}