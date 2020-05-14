package com.jdeveloperapps.kotlinproject.data.providers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jdeveloperapps.kotlinproject.data.entity.Note
import com.jdeveloperapps.kotlinproject.data.entity.User
import com.jdeveloperapps.kotlinproject.data.errors.NoAuthExeption
import com.jdeveloperapps.kotlinproject.data.model.NoteResult
import timber.log.Timber

class FireStoreProvider : RemoteDataProvider {

    companion object {
        private const val NOTES_COLLECTION = "notes"
        private const val USERS_COLLECTION = "users"
    }

    private val store = FirebaseFirestore.getInstance()


    private val currentUser get() = FirebaseAuth.getInstance().currentUser

    override fun getCurrentUser(): LiveData<User?> = MutableLiveData<User?>().apply {
        value = currentUser?.let {
            User(it.displayName?: "", it.email ?: "")
        }
    }

    private fun getUserNotesCollection() = currentUser?.let {
        store.collection(USERS_COLLECTION).document(it.uid).collection(USERS_COLLECTION)
    } ?: throw NoAuthExeption()

    override fun subscribeToAllNotes(): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()
        getUserNotesCollection().addSnapshotListener { querySnapshot, e ->
            e?.let {
                result.value = NoteResult.Error(e)
            } ?: let {
                querySnapshot?.let {
                    val notes = querySnapshot.map { it.toObject(Note::class.java) }
                    result.value = NoteResult.Success(notes)
                }
            }
        }
        return result
    }

    override fun getNoteById(id: String): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()

        getUserNotesCollection().document(id)
                .get().addOnSuccessListener {snapshot ->
            result.value = NoteResult.Success(snapshot.toObject(Note::class.java))
        }.addOnFailureListener {
            Timber.e("Error saving, message - ${it.message}")
            result.value = NoteResult.Error(it)
        }

        return result
    }

    override fun saveNote(note: Note): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()

        getUserNotesCollection().document(note.id).set(note).addOnSuccessListener {
            Timber.d("Note ${note.title} is saved")
            result.value = NoteResult.Success(note)
        }.addOnFailureListener {
            Timber.e("Error saving ${note.title}, message - ${it.message}")
            result.value = NoteResult.Error(it)
        }

        return result
    }


}