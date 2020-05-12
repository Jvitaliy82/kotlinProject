package com.jdeveloperapps.kotlinproject.ui.note

import com.jdeveloperapps.kotlinproject.data.entity.Note
import com.jdeveloperapps.kotlinproject.ui.base.BaseViewState

class NoteViewState(note: Note? = null, error: Throwable? = null) : BaseViewState<Note?>(note, error) {

}