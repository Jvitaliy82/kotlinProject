package com.jdeveloperapps.kotlinproject.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jdeveloperapps.kotlinproject.data.entity.Note
import java.util.*

object NotesRepository {

    private val notesLiveData = MutableLiveData<List<Note>>()

    private val notes = mutableListOf(
            Note(
                    UUID.randomUUID().toString(),
                    "Вторая заметка",
                    "Текст второй заметки. Короткий, но интересный",
                    Note.Color.BLUE
            ),
            Note(
                    UUID.randomUUID().toString(),
                    "Третья заметка",
                    "Текст третьей заметки. Короткий, но интересный",
                    Note.Color.GREEN
            ),
            Note(
                    UUID.randomUUID().toString(),
                    "Четвертая заметка",
                    "Текст четвертой заметки. Короткий, но интересный",
                    Note.Color.YELLOW
            ),
            Note(
                    UUID.randomUUID().toString(),
                    "Пятая заметка",
                    "Текст пятой заметки. Короткий, но интересный",
                    Note.Color.PINK
            ),
            Note(
                    UUID.randomUUID().toString(),
                    "Шестая заметка",
                    "Текст шестой заметки. Короткий, но интересный",
                    Note.Color.VIOLET
            ),
            Note(
                    UUID.randomUUID().toString(),
                    "Шестая заметка",
                    "Текст шестой заметки. Короткий, но интересный",
                    Note.Color.RED
            )

    )

    init {
        notesLiveData.value = notes
    }

    fun getNotes(): LiveData<List<Note>> {
        return notesLiveData
    }

    fun saveNote(note: Note) {
        addOrReplece(note)
        notesLiveData.value = notes
    }

    private fun addOrReplece(note: Note) {
        for(i in notes.indices) {
            if (notes[i] == note) {
                notes[i] = note
                return
            }
        }
        notes.add(note)
    }
}