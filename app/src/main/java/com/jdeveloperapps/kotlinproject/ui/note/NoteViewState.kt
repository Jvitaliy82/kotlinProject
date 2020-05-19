package com.jdeveloperapps.kotlinproject.ui.note

import com.jdeveloperapps.kotlinproject.data.entity.Note
import com.jdeveloperapps.kotlinproject.ui.base.BaseViewState

class NoteViewState(data: Data = Data(), error: Throwable? = null) : BaseViewState<NoteViewState.Data>(data, error){
    data class Data(val isDeleted: Boolean = false, val note: Note? = null)
}