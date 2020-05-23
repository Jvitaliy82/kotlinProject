package com.jdeveloperapps.kotlinproject.ui.note

import com.jdeveloperapps.kotlinproject.data.entity.Note

data class NoteData(val isDeleted: Boolean = false, val note: Note? = null)