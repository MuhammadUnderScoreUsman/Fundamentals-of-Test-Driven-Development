package com.mohammadosman.unittest.database

import androidx.room.*
import com.mohammadosman.unittest.database.model.NoteDto

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(noteDto: NoteDto): Long

    @Query("Update note_table SET title = :title, content = :content where id = :id")
    suspend fun updateNote(id: String, title: String, content: String): Int

    @Delete
    suspend fun deleteNote(noteDto: NoteDto): Int

    @Query("Select * from note_table where id = :id")
    suspend fun getNoteViaId(id: String): NoteDto?

    @Query("Select * from note_table where title like '%' || :queryTitle || '%'")
    suspend fun searchNote(queryTitle: String): List<NoteDto>

    @Query("Select * from note_table")
    suspend fun getAllNotes(): List<NoteDto>

}