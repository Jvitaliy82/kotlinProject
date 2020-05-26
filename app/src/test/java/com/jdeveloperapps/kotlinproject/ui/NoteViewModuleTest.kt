package com.jdeveloperapps.kotlinproject.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jdeveloperapps.kotlinproject.data.NotesRepository
import com.jdeveloperapps.kotlinproject.data.entity.Note
import com.jdeveloperapps.kotlinproject.ui.note.NoteData
import com.jdeveloperapps.kotlinproject.ui.note.NoteViewModel
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NoteViewModuleTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val mockRepository = mockk<NotesRepository>()

    private val testNote = Note("1", "title1", "text1")
    private lateinit var viewModel: NoteViewModel

    @Before
    fun setup() {
        clearAllMocks()
        coEvery { mockRepository.getNoteById(testNote.id) } returns testNote
        coEvery { mockRepository.deleteNote(testNote.id) } just runs
        viewModel = NoteViewModel(mockRepository)
    }

    @Test
    fun `loadNote should return Note Data`() = runBlocking {
        var result: NoteData? = null
        val testData = NoteData(false, testNote)
        viewModel.loadNote(testNote.id)
        result = viewModel.getViewState().receive()
        assertEquals(testData, result)
    }

    @Test
    fun `loadNote should return Note Data with isDeleted`() = runBlocking {
        var result: NoteData? = null
        val testData = NoteData(true, null)
        viewModel.deleteNote()
        result = viewModel.getViewState().receive()
        assertEquals(testData, result)
    }

}