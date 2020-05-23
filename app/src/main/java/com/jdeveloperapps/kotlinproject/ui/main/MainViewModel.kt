package com.jdeveloperapps.kotlinproject.ui.main

import androidx.annotation.VisibleForTesting
import com.jdeveloperapps.kotlinproject.data.NotesRepository
import com.jdeveloperapps.kotlinproject.data.entity.Note
import com.jdeveloperapps.kotlinproject.data.model.NoteResult
import com.jdeveloperapps.kotlinproject.ui.base.BaseViewModel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

class MainViewModel(notesRepository: NotesRepository): BaseViewModel<List<Note>?>() {

    private val notesChannel = notesRepository.getNotes()

    init {
        launch {
            notesChannel.consumeEach {
                when(it) {
                    is NoteResult.Success<*> -> setData(it.data as? List<Note>)
                    is NoteResult.Error -> setError(it.error)
                }
            }
        }
    }

    @VisibleForTesting
    public override fun onCleared() {
        super.onCleared()
        notesChannel.cancel()
    }

}