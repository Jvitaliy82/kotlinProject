package com.jdeveloperapps.kotlinproject.ui.splash

import com.jdeveloperapps.kotlinproject.data.NotesRepository
import com.jdeveloperapps.kotlinproject.data.errors.NoAuthExeption
import com.jdeveloperapps.kotlinproject.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class SplashViewModel(val notesRepository: NotesRepository): BaseViewModel<Boolean?>() {

    fun requestUser() {
        launch {
            notesRepository.getCurrentUser()?.let {
                setData(true)
            } ?: setError(NoAuthExeption())
        }
    }

}