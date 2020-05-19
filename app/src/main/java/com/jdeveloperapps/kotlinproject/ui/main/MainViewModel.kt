package com.jdeveloperapps.kotlinproject.ui.main

import androidx.lifecycle.Observer
import com.jdeveloperapps.kotlinproject.data.NotesRepository
import com.jdeveloperapps.kotlinproject.data.entity.Note
import com.jdeveloperapps.kotlinproject.data.model.NoteResult
import com.jdeveloperapps.kotlinproject.ui.base.BaseViewModel

class MainViewModel(val notesRepository: NotesRepository): BaseViewModel<List<Note>?, MainViewState>() {

    private val repositoryNotes = notesRepository.getNotes()

    private val notesObserver = object : Observer<NoteResult> {
        override fun onChanged(t: NoteResult?) {
            t ?: return
            when(t) {
                is NoteResult.Success<*> -> {
                    viewStateLiveData.value = MainViewState(notes = t.data as? List<Note>)
                }
                is NoteResult.Error -> {
                    viewStateLiveData.value = MainViewState(error = t.error)
                }
            }
        }

    }

    init {
        viewStateLiveData.value = MainViewState()
        repositoryNotes.observeForever(notesObserver)
    }

    override fun onCleared() {
        super.onCleared()
        repositoryNotes.removeObserver(notesObserver)
    }

}