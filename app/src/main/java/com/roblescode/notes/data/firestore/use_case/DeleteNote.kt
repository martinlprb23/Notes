package com.roblescode.notes.data.firestore.use_case

import com.roblescode.notes.data.firestore.repository.NotesRepository

class DeleteNote(
    private val repo : NotesRepository
) {
    suspend operator fun invoke(uid: String, noteId: String) = repo.deleteNoteToFirestore(uid, noteId)
}
