package com.jdeveloperapps.kotlinproject.ui.splash

import com.jdeveloperapps.kotlinproject.data.NotesRepository
import com.jdeveloperapps.kotlinproject.data.errors.NoAuthExeption
import com.jdeveloperapps.kotlinproject.ui.base.BaseViewModel

class SplashViewModel(val notesRepository: NotesRepository): BaseViewModel<Boolean?, SplashViewState>() {

    fun requestUser() {
        notesRepository.getCurrentUser().observeForever {
            viewStateLiveData.value = it?.let {
                SplashViewState(true)
            } ?: let {
                SplashViewState(error = NoAuthExeption())
            }
        }
    }

}