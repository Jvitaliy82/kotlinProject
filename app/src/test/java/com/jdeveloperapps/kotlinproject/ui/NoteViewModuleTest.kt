package com.jdeveloperapps.kotlinproject.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jdeveloperapps.kotlinproject.data.NotesRepository
import com.jdeveloperapps.kotlinproject.data.entity.Note
import com.jdeveloperapps.kotlinproject.ui.note.NoteViewModel
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule

class NoteViewModuleTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val mockRepository = mockk<NotesRepository>()
//    private val noteLiveData = MutableLiveData<NoteResult>()

    private val testNote = Note("1", "title1", "text1")
    private lateinit var viewModel: NoteViewModel

    @Before
    fun setup() {
        clearAllMocks()
        every { runBlocking { mockRepository.getNoteById(testNote.id) } } returns testNote
//        every { mockRepository.deleteNote(testNote.id) } returns noteLiveData
        viewModel = NoteViewModel(mockRepository)
    }

//    @Test
//    fun `loadNote should return Note Data`() {
//        var result: NoteData? = null
//        val testData = NoteData(false, testNote)
//        viewModel.getViewState() {
//            result = it?.data
//        }
//        viewModel.loadNote(testNote.id)
//        noteLiveData.value = NoteResult.Success(testNote)
//        assertEquals(testData, result)
//    }

//    @Test
//    fun `loadNote should return error`() {
//        var result: Throwable? = null
//        val testData = Throwable("error")
//        viewModel.getViewState().observeForever {
//            result = it?.error
//        }
//        viewModel.loadNote(testNote.id)
//        noteLiveData.value = NoteResult.Error(error = testData)
//        assertEquals(testData, result)
//    }
//
//    @Test
//    fun `deleteNote should return Note Data with isDeleted`() {
//        var result: NoteViewState.Data? = null
//        val testData = NoteViewState.Data(true, null)
//        viewModel.getViewState().observeForever {
//            result = it?.data
//        }
//        viewModel.save(testNote)
//        viewModel.deleteNote()
//        noteLiveData.value = NoteResult.Success(null)
//        assertEquals(testData, result)
//    }
//
//    @Test
//    fun `deleteNote should return error`() {
//        var result: Throwable? = null
//        val testData = Throwable("error")
//        viewModel.getViewState().observeForever {
//            result = it?.error
//        }
//        viewModel.save(testNote)
//        viewModel.deleteNote()
//        noteLiveData.value = NoteResult.Error(error = testData)
//        assertEquals(testData, result)
//    }

}