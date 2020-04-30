package com.jdeveloperapps.kotlinproject.ui.note

import androidx.lifecycle.ViewModel
import com.jdeveloperapps.kotlinproject.data.NotesRepository
import com.jdeveloperapps.kotlinproject.data.entity.Note

class NoteViewModel: ViewModel() {

    private var pendingNote: Note? = null

    fun save(note: Note) {
        pendingNote = note
    }

    override fun onCleared() {
        pendingNote?.let {
            NotesRepository.saveNote(it)
        }
    }

}