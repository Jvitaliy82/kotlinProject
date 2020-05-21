package com.jdeveloperapps.kotlinproject.data.providers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jdeveloperapps.kotlinproject.data.entity.Note
import com.jdeveloperapps.kotlinproject.data.entity.User
import com.jdeveloperapps.kotlinproject.data.errors.NoAuthExeption
import com.jdeveloperapps.kotlinproject.data.model.NoteResult

class FireStoreProvider(
        private val firebaseAuth: FirebaseAuth,
        private val firebaseStore: FirebaseFirestore
) : RemoteDataProvider {

    companion object {
        private const val NOTES_COLLECTION = "notes"
        private const val USERS_COLLECTION = "users"
    }

    private val currentUser get() = firebaseAuth.currentUser

    override fun getCurrentUser(): LiveData<User?> = MutableLiveData<User?>().apply {
        value = currentUser?.let {
            User(it.displayName?: "", it.email ?: "")
        }
    }

    private fun getUserNotesCollection() = currentUser?.let {
        firebaseStore.collection(USERS_COLLECTION).document(it.uid).collection(NOTES_COLLECTION)
    } ?: throw NoAuthExeption()

    override fun subscribeToAllNotes(): LiveData<NoteResult> = MutableLiveData<NoteResult>().apply{
        try {
            getUserNotesCollection().addSnapshotListener { querySnapshot, e ->
                e?.let {
                    value = NoteResult.Error(e)
                } ?: let {
                    querySnapshot?.let {
                        val notes = querySnapshot.documents.map { it.toObject(Note::class.java) }
                        value = NoteResult.Success(notes)
                    }
                }
            }
        } catch (e: Throwable) {
            value = NoteResult.Error(e)
        }
    }

    override fun getNoteById(id: String): LiveData<NoteResult> = MutableLiveData<NoteResult>().apply{
        try {
            getUserNotesCollection().document(id)
                    .get().addOnSuccessListener {snapshot ->
                value = NoteResult.Success(snapshot.toObject(Note::class.java))
            }.addOnFailureListener {
                value = NoteResult.Error(it)
            }
        } catch (e: Throwable) {
            value = NoteResult.Error(e)
        }
    }

    override fun saveNote(note: Note): LiveData<NoteResult> = MutableLiveData<NoteResult>().apply{
        try {
            getUserNotesCollection().document(note.id).set(note).addOnSuccessListener {
                value = NoteResult.Success(note)
            }.addOnFailureListener {
                value = NoteResult.Error(it)
            }
        } catch (e: Throwable) {
            value = NoteResult.Error(e)
        }
    }

    override fun deleteNote(noteId: String): LiveData<NoteResult> = MutableLiveData<NoteResult>().apply {
        try {
            getUserNotesCollection().document(noteId).delete()
                    .addOnSuccessListener {
                value = NoteResult.Success(null)
            }.addOnFailureListener {
                value = NoteResult.Error(it)
            }
        } catch (e: Throwable) {
            value = NoteResult.Error(e)
        }
    }

}