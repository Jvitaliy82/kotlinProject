package com.jdeveloperapps.kotlinproject.ui.note

import androidx.annotation.VisibleForTesting
import com.jdeveloperapps.kotlinproject.data.NotesRepository
import com.jdeveloperapps.kotlinproject.data.entity.Note
import com.jdeveloperapps.kotlinproject.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class NoteViewModel(val notesRepository: NotesRepository): BaseViewModel<NoteData>() {

    private val pendingNote: Note?
        get() = getViewState().poll()?.note

    fun save(note: Note) {
        setData(NoteData(note = note))
    }

    fun loadNote(noteId: String) {
        launch {
            try {
                notesRepository.getNoteById(noteId).let {
                    setData(NoteData(note = it))
                }
            } catch (e: Exception) {
                setError(e)
            }
        }
    }

    fun deleteNote() {
        launch {
            try {
                pendingNote?.let {
                    notesRepository.deleteNote(it.id)
                }
                setData(NoteData(isDeleted = true))
            } catch (e: Exception) {
                setError(e)
            }
        }
    }

    @VisibleForTesting
    public override fun onCleared() {
        launch {
            pendingNote?.let {
                notesRepository.saveNote(it)
                super.onCleared()
            }
        }
    }

}