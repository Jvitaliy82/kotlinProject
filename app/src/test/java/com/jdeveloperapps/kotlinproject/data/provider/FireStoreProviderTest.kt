package com.jdeveloperapps.kotlinproject.data.provider

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.jdeveloperapps.kotlinproject.data.entity.Note
import com.jdeveloperapps.kotlinproject.data.providers.FireStoreProvider
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule

class FireStoreProviderTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val mockDb = mockk<FirebaseFirestore>()
    private val mockAuth = mockk<FirebaseAuth>()

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
        every { mockAuth.currentUser } returns mockUser
        every { mockUser.uid} returns ""
        every { mockDb.collection(any()).document(any()).collection(any()) } returns mockResultCollection

        every { mockDocument1.toObject(Note::class.java) } returns testNotes[0]
        every { mockDocument2.toObject(Note::class.java) } returns testNotes[1]
        every { mockDocument3.toObject(Note::class.java) } returns testNotes[2]
    }

//    @Test
//    fun `should throw NoAuthException if no auth`() {
//        every { mockAuth.currentUser } returns null
//        var result: Any? = null
//        provider.subscribeToAllNotes().observeForever {
//            result = (it as? NoteResult.Error)?.error
//        }
//        assertTrue(result is NoAuthExeption)
//    }
//
//    @Test
//    fun `save note calls set`() {
//        val mockDocumentReference = mockk<DocumentReference>()
//        every { mockResultCollection.document(testNotes[0].id) } returns mockDocumentReference
//        provider.saveNote(testNotes[0])
//        verify (exactly = 1){ mockDocumentReference.set(testNotes[0]) }
//    }
//
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
//    @Test
//    fun `saveNote returns Note`() {
//        var result: Note? = null
//        val mockDocumentReference = mockk<DocumentReference>()
//        val slot = slot<OnSuccessListener<in Void>>()
//
//        every { mockResultCollection.document(testNotes[0].id) } returns mockDocumentReference
//        every { mockDocumentReference.set(testNotes[0]).addOnSuccessListener(capture(slot)) } returns mockk()
//
//        provider.saveNote(testNotes[0]).observeForever {
//            result = (it as? NoteResult.Success<Note>)?.data
//        }
//
//        slot.captured.onSuccess(null)
//        assertEquals(testNotes[0], result)
//    }
//
//    @Test
//    fun `deleteNote returns Note`() {
//        var result: NoteResult? = null
//        val mockDocumentReference = mockk<DocumentReference>()
//        val slot = slot<OnSuccessListener<in Void>>()
//
//        every { mockResultCollection.document(testNotes[0].id) } returns mockDocumentReference
//        every { mockDocumentReference.delete().addOnSuccessListener(capture(slot)) } returns mockk()
//
//        provider.deleteNote(testNotes[0].id).observeForever {
//            result = it
//        }
//
//        slot.captured.onSuccess(null)
//        assertTrue(result is NoteResult.Success<*>)
//    }
}