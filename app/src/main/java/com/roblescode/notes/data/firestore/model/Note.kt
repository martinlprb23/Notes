package com.roblescode.notes.data.firestore.model

data class Note(
    var id: String? = null,
    var title: String? = null,
    var description: String? = null,
    var color: String? = null,
    var label: String? = null,
    var date: String? = null
)
