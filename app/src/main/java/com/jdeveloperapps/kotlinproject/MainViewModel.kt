package com.jdeveloperapps.kotlinproject

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val viewStateLiveData = MutableLiveData<Int>()

    init {
        viewStateLiveData.value = 1
    }

    fun viewState() : LiveData<Int> = viewStateLiveData

    fun upCounter() {
        viewStateLiveData.value = viewStateLiveData.value?.plus(1)
    }

}