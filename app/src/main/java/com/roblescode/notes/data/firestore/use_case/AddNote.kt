package com.roblescode.notes.data.firestore.use_case

import com.roblescode.notes.data.firestore.repository.NotesRepository

class AddNote(
    private val repo: NotesRepository
) {
    suspend operator fun invoke(
        uid: String,
        title: String,
        description: String,
        label: String,
        color: String
    ) = repo.addNoteToFirestore(
        uid = uid,
        title = title,
        description = description,
        label = label,
        color = color
    )
}