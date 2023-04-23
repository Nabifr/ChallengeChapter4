package com.android.challengechapter4.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDAO {

    @Insert
    fun insertNote(note: NoteData)

    @Query("SELECT * FROM NoteData ORDER BY title DESC ")
    fun getNoteDesc(): List<NoteData>

    @Query("SELECT * FROM NoteData ORDER BY title ASC ")
    fun getNoteAsc(): List<NoteData>

    @Delete
    fun deleteNote(note: NoteData): Int

    @Update
    fun updateNote(note: NoteData): Int
}