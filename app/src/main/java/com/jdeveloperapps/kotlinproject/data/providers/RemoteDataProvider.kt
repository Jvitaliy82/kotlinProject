package com.jdeveloperapps.kotlinproject.data.providers

import androidx.lifecycle.LiveData
import com.jdeveloperapps.kotlinproject.data.entity.Note
import com.jdeveloperapps.kotlinproject.data.model.NoteResult

interface RemoteDataProvider {
    fun subscribeToAllNotes(): LiveData<NoteResult>
    fun getNoteById(id: String): LiveData<NoteResult>
    fun saveNote(note: Note): LiveData<NoteResult>
}