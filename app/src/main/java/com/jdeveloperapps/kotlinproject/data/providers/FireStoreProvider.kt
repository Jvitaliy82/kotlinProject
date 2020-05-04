package com.jdeveloperapps.kotlinproject.data.providers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.jdeveloperapps.kotlinproject.data.entity.Note
import com.jdeveloperapps.kotlinproject.data.model.NoteResult
import timber.log.Timber

class FireStoreProvider : RemoteDataProvider {

    companion object {
        private const val NOTES_COLLECTION = "notes"
    }

    private val store = FirebaseFirestore.getInstance()
    private val notesReference = store.collection(NOTES_COLLECTION)

    override fun subscribeToAllNotes(): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()
        notesReference.addSnapshotListener { querySnapshot, e ->
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

        notesReference.document(id)
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

        notesReference.document(note.id).set(note).addOnSuccessListener {
            Timber.d("Note ${note.title} is saved")
            result.value = NoteResult.Success(note)
        }.addOnFailureListener {
            Timber.e("Error saving ${note.title}, message - ${it.message}")
            result.value = NoteResult.Error(it)
        }

        return result
    }
}