package com.mohammadosman.unittest

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mohammadosman.unittest.model.Note


class NoteFactory(
    private val classLoader: ClassLoader
) {

    fun noteList(): List<Note> {
        val list: List<Note> = Gson().fromJson(
            getNoteFromFile("note_list.json"),
            object : TypeToken<List<Note>>() {}.type
        )
        return list
    }

    fun produceData(list: List<Note>): HashMap<String, Note> {
        val map = HashMap<String, Note>()
        for (note in list) {
            map.put(note.id, note)
        }
        return map
    }

    private fun getNoteFromFile(file: String?): String {
        return classLoader.getResource(file).readText()
    }

}