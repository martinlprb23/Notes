package com.roblescode.notes.data.firestore.repository

import com.roblescode.notes.data.firestore.model.Note
import com.roblescode.notes.data.firestore.model.Response
import kotlinx.coroutines.flow.Flow


typealias Notes = List<Note>
typealias NoteResponse = Response<Note?>
typealias NotesResponse = Response<Notes>
typealias UpdateNoteResponse = Response<Boolean>
typealias AddNotesResponse = Response<Boolean>
typealias DeleteNotesResponse = Response<Boolean>

interface NotesRepository {

    fun getNotesFromFirestore(uid: String): Flow<NotesResponse>

    fun getNoteById(uid: String, docId: String) : Flow<NoteResponse>

    suspend fun addNoteToFirestore(
        uid: String,
        title: String,
        description: String,
        label: String,
        color: String
    ): AddNotesResponse

    suspend fun updateNote(
        uid: String,
        idNote: String,
        title: String,
        description: String,
        label: String,
        color: String
    ): UpdateNoteResponse

    suspend fun deleteNoteToFirestore(uid: String, noteId: String): DeleteNotesResponse
}



