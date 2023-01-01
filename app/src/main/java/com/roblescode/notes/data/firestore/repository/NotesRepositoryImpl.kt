package com.roblescode.notes.data.firestore.repository

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.roblescode.notes.data.firestore.model.Note
import com.roblescode.notes.data.firestore.model.Response
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotesRepositoryImpl @Inject constructor(
    private val notesRef: CollectionReference
) : NotesRepository {

    override fun getNotesFromFirestore(uid: String) = callbackFlow {
        val query =
            notesRef.document(uid).collection("notes").orderBy("date", Query.Direction.DESCENDING)
        val snapshotListener = query.addSnapshotListener { snapshot, e ->
            val notesResponse = if (snapshot != null) {
                val notes = snapshot.toObjects(Note::class.java)
                Response.Success(notes)
            } else {
                Response.Failure(e)
            }
            trySend(notesResponse)
        }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override fun getNoteById(uid: String, docId: String) = callbackFlow {
        val query =
            notesRef.document(uid).collection("notes").document(docId)
        val snapshotListener = query.addSnapshotListener { snapshot, e ->
            val noteResponse = if (snapshot != null) {
                val note = snapshot.toObject(Note::class.java)
                Log.i("Note", note.toString())
                Response.Success(note)
            } else {
                Response.Failure(e)
            }
            trySend(noteResponse)
        }
        awaitClose {
            snapshotListener.remove()
        }
    }

/* TODO implementar esta mmda
override suspend fun getNoteById(uid: String, noteId: String): Note? {
    val query = notesRef.document(uid).collection("notes").document(noteId)
    return query.get().await().toObject<Note>()
}*/


override suspend fun addNoteToFirestore(
    uid: String, title: String, description: String, label: String, color: String
): AddNotesResponse {
    return try {
        val id = notesRef.document().id
        val note = Note(
            id = id,
            title = title,
            description = description,
            label = label,
            color = color,
            date = Timestamp.now().toDate().toString()
        )
        // notesRef.document(id).set(note).await()
        notesRef.document(uid).collection("notes").document(id).set(note).await()
        Response.Success(true)
    } catch (e: Exception) {
        Response.Failure(e)
    }
}

override suspend fun updateNote(
    uid: String,
    idNote: String,
    title: String,
    description: String,
    label: String,
    color: String
): UpdateNoteResponse {
    return try {
        notesRef.document(uid).collection("notes").document(idNote).update(
            "color", color, "description", description, "label", label, "title", title
        ).await()
        Response.Success(true)
    } catch (e: Exception) {
        Response.Failure(e)
    }
}

override suspend fun deleteNoteToFirestore(uid: String, noteId: String): DeleteNotesResponse {
    return try {
        notesRef.document(uid).collection("notes").document(noteId).delete().await()
        Response.Success(true)
    } catch (e: Exception) {
        Response.Failure(e)
    }
}


}