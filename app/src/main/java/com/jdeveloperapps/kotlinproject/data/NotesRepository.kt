package com.jdeveloperapps.kotlinproject.data

import com.jdeveloperapps.kotlinproject.data.entity.Note
import com.jdeveloperapps.kotlinproject.data.providers.RemoteDataProvider


class NotesRepository(val remoteProvider: RemoteDataProvider) {

    fun getNotes() = remoteProvider.subscribeToAllNotes()
    fun saveNote(note: Note) = remoteProvider.saveNote(note)
    fun getNoteById(id: String) = remoteProvider.getNoteById(id)
    fun getCurrentUser() = remoteProvider.getCurrentUser()
    fun deleteNote(noteId: String) = remoteProvider.deleteNote(noteId)
}