package com.roblescode.notes.data.firestore.use_case

import com.roblescode.notes.data.firestore.repository.NotesRepository

class UpdateNote(
    private val repo: NotesRepository
) {
    suspend operator fun invoke(
        uid: String,
        idNote: String,
        title: String,
        description: String,
        label: String,
        color: String
    ) = repo.updateNote(
        uid = uid,
        idNote = idNote,
        title = title,
        description = description,
        label = label,
        color = color
    )
}

