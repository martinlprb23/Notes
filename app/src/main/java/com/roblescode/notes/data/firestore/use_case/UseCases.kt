package com.roblescode.notes.data.firestore.use_case


data class UseCases(
    val getNotes: GetNotes,
    val getNoteById: GetNoteById,
    val addNote: AddNote,
    val updateNote: UpdateNote,
    val deleteNote: DeleteNote
)
