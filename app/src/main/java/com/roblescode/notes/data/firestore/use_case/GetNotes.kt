package com.roblescode.notes.data.firestore.use_case

import com.roblescode.notes.data.firestore.repository.NotesRepository

class GetNotes(
    private val repo: NotesRepository
) {
    operator fun invoke(uid: String) = repo.getNotesFromFirestore(uid = uid)
}


class GetNoteById(
    private val repo: NotesRepository
) {
    operator fun invoke(uid: String, docId: String) = repo.getNoteById(uid = uid, docId = docId)
}