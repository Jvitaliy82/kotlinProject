package com.jdeveloperapps.kotlinproject.data.provider

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
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

    private val mockDocument1 = mockk<DocumentReference>()
    private val mockDocument2 = mockk<DocumentReference>()
    private val mockDocument3 = mockk<DocumentReference>()

    private val testNotes = listOf(Note("1"), Note("2"), Note("3"))

    private val provider = FireStoreProvider(mockAuth, mockDb)

    @Before
    fun setup() {
        clearAllMocks()
        every { mockAuth.currentUser } returns mockUser
    }

}