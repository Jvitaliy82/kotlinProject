package com.jdeveloperapps.kotlinproject.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.jdeveloperapps.kotlinproject.data.NotesRepository
import com.jdeveloperapps.kotlinproject.data.model.NoteResult
import com.jdeveloperapps.kotlinproject.ui.main.MainViewModel
import io.mockk.mockk
import org.junit.Rule

class MainViewModuleTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val mockRepository = mockk<NotesRepository>()
    private val notesLiveData = MutableLiveData<NoteResult>()

    private lateinit var viewModel: MainViewModel

//    @Before
//    fun setup() {
//        clearAllMocks()
//        every { mockRepository.getNotes() } returns notesLiveData
//        viewModel = MainViewModel(mockRepository)
//    }
//
//    @Test
//    fun `should call getNotes once`() {
//        verify(exactly = 1) { mockRepository.getNotes() }
//    }
//
//    @Test
//    fun `should return notes`() {
//        var result: List<Note>? = null
//        val testData = listOf(Note("1"), Note("2"))
//        viewModel.getViewState().observeForever {
//            result = it?.data
//        }
//        notesLiveData.value = NoteResult.Success(testData)
//        assertEquals(testData, result)
//    }
//
//    @Test
//    fun `should return error`() {
//        var result: Throwable? = null
//        val testData = Throwable("error")
//        viewModel.getViewState().observeForever {
//            result = it?.error
//        }
//        notesLiveData.value = NoteResult.Error(error = testData)
//        assertEquals(testData, result)
//    }
//
//    @Test
//    fun `should remove observer`() {
//        viewModel.onCleared()
//        assertFalse(notesLiveData.hasObservers())
//    }

}