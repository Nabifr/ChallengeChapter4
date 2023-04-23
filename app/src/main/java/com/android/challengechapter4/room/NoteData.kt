package com.android.challengechapter4.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity
data class NoteData (
    @PrimaryKey(autoGenerate = true)
    var id : Int,
    var title : String,
    var content : String
) : Serializable