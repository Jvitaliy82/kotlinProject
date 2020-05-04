package com.jdeveloperapps.kotlinproject.data

import com.jdeveloperapps.kotlinproject.data.entity.Note
import com.jdeveloperapps.kotlinproject.data.providers.FireStoreProvider
import com.jdeveloperapps.kotlinproject.data.providers.RemoteDataProvider


object NotesRepository {

    val remoteProvider: RemoteDataProvider = FireStoreProvider()

    fun getNotes() = remoteProvider.subscribeToAllNotes()
    fun saveNote(note: Note) = remoteProvider.saveNote(note)
    fun getNoteById(id: String) = remoteProvider.getNoteById(id)

}