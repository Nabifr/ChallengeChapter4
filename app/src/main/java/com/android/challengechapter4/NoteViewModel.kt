package com.android.challengechapter4

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.challengechapter4.room.NoteData
import com.android.challengechapter4.room.NoteDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NoteViewModel(app: Application) : AndroidViewModel(app) {

    var allNote: MutableLiveData<List<NoteData>> = MutableLiveData()

    init {
        getAllNote()
    }

    fun getAllNoteObservers(): MutableLiveData<List<NoteData>> {
        return allNote
    }

    fun getAllNote() {
        GlobalScope.launch {
            val userDao = NoteDatabase.getInstance(getApplication())!!.noteDao()
            val listNote = userDao.getNoteAsc()
            allNote.postValue(listNote)
        }
    }

    fun insertNote(entity: NoteData) {
        val noteDao = NoteDatabase.getInstance(getApplication())?.noteDao()
        noteDao!!.insertNote(entity)
        getAllNote()
    }

    fun deleteNote(entity: NoteData) {
        val userDao = NoteDatabase.getInstance(getApplication())!!.noteDao()
        userDao?.deleteNote(entity)
        getAllNote()
    }

    fun updateNote(entity: NoteData) {
        val userDao = NoteDatabase.getInstance(getApplication())!!.noteDao()
        userDao?.updateNote(entity)
        getAllNote()
    }

}