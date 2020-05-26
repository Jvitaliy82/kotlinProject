package com.jdeveloperapps.kotlinproject.data.provider

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.jdeveloperapps.kotlinproject.data.entity.Note
import com.jdeveloperapps.kotlinproject.data.errors.NoAuthExeption
import com.jdeveloperapps.kotlinproject.data.model.NoteResult
import com.jdeveloperapps.kotlinproject.data.providers.FireStoreProvider
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FireStoreProviderTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val mockAuth = mockk<FirebaseAuth>()
    private val mockDb = mockk<FirebaseFirestore>()

    private val mockResultCollection = mockk<CollectionReference>()
    private val mockUser = mockk<FirebaseUser>()

    private val mockDocument1 = mockk<DocumentSnapshot>()
    private val mockDocument2 = mockk<DocumentSnapshot>()
    private val mockDocument3 = mockk<DocumentSnapshot>()

    private val testNotes = listOf(Note("1"), Note("2"), Note("3"))

    private val provider = FireStoreProvider(mockAuth, mockDb)

    @Before
    fun setup() {
        clearAllMocks()
        coEvery { mockAuth.currentUser } returns mockUser
        coEvery { mockUser.uid } returns ""
        coEvery { mockDb.collection(any()).document(any()).collection(any()) } returns mockResultCollection

        coEvery { mockDocument1.toObject(Note::class.java) } returns testNotes[0]
        coEvery { mockDocument2.toObject(Note::class.java) } returns testNotes[1]
        coEvery { mockDocument3.toObject(Note::class.java) } returns testNotes[2]
    }

    @Test
    fun `should throw Error if no auth`() = runBlocking {
        coEvery { mockAuth.currentUser } returns null
        var result: Any? = null
        result = provider.subscribeToAllNotes().receive()
        assertTrue(result is NoteResult.Error)
    }

//    @Test
//    fun `subscribeToAllNotes returns notes`() {
//        var result: List<Note>? = null
//        val mockSnapshot = mockk<QuerySnapshot>()
//        val slot = slot<EventListener<QuerySnapshot>>()
//
//        every { mockSnapshot.documents } returns listOf(mockDocument1, mockDocument2, mockDocument3)
//        every { mockResultCollection.addSnapshotListener(capture(slot)) } returns mockk()
//
//        provider.subscribeToAllNotes().observeForever {
//            result = (it as? NoteResult.Success<List<Note>>)?.data
//        }
//
//        slot.captured.onEvent(mockSnapshot, null)
//        assertEquals(testNotes, result)
//    }
//
//
//    @Test
//    fun `subscribeToAllNotes returns error`() {
//        var result: Throwable? = null
//        val mockExeption = mockk<FirebaseFirestoreException>()
//        val slot = slot<EventListener<QuerySnapshot>>()
//
//        every { mockResultCollection.addSnapshotListener(capture(slot)) } returns mockk()
//
//        provider.subscribeToAllNotes().observeForever {
//            result = (it as? NoteResult.Error)?.error
//        }
//
//        slot.captured.onEvent(null, mockExeption)
//        assertEquals(result, mockExeption)
//    }
//
    @Test
    fun `saveNote returns Note`() = runBlocking{
        var result: Note? = null
        val mockDocumentReference = mockk<DocumentReference>()
        val slot = slot<OnSuccessListener<in Void>>()

        coEvery { mockResultCollection.document(testNotes[0].id) } returns mockDocumentReference
        coEvery { mockDocumentReference.set(testNotes[0]).addOnSuccessListener(capture(slot)) } returns mockk()

        result = provider.saveNote(testNotes[0])

        slot.captured.onSuccess(null)
        assertEquals(testNotes[0], result)
    }


}