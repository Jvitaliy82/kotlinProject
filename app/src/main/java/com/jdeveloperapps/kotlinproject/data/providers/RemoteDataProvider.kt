package com.jdeveloperapps.kotlinproject.data.providers

import com.jdeveloperapps.kotlinproject.data.entity.Note
import com.jdeveloperapps.kotlinproject.data.entity.User
import com.jdeveloperapps.kotlinproject.data.model.NoteResult
import kotlinx.coroutines.channels.ReceiveChannel

interface RemoteDataProvider {
    fun subscribeToAllNotes(): ReceiveChannel<NoteResult>
    suspend fun getNoteById(id: String): Note
    suspend fun saveNote(note: Note): Note
    suspend fun getCurrentUser(): User?
    suspend fun deleteNote(noteId: String)
}