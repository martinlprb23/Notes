package com.roblescode.notes.presentation.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roblescode.notes.data.firestore.model.Response
import com.roblescode.notes.data.firestore.repository.*
import com.roblescode.notes.data.firestore.use_case.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {


    var notesResponse by mutableStateOf<NotesResponse>(Response.Loading)
        private set
    var noteResponse by mutableStateOf<NoteResponse>(Response.Loading)
        private set
    var addNoteResponse by mutableStateOf<AddNotesResponse>(Response.Success(false))
        private set
    var updateNoteResponse by mutableStateOf<UpdateNoteResponse>(Response.Success(false))
        private set
    var deleteNoteResponse by mutableStateOf<DeleteNotesResponse>(Response.Success(false))
        private set

    fun getNotes(uid: String) = viewModelScope.launch {
        useCases.getNotes(uid = uid).collect {
            notesResponse = it
        }
    }

    fun getNoteById(uid: String, docId: String)= viewModelScope.launch{
        useCases.getNoteById(uid = uid, docId = docId).collect{
            noteResponse = it
        }
    }


    fun updateNote(uid: String, noteId: String, title: String, label: String, description: String, color: String) =
        viewModelScope.launch {
        updateNoteResponse = Response.Loading
        updateNoteResponse = useCases.updateNote(
            uid = uid,
            idNote = noteId,
            title = title,
            description = description,
            label = label,
            color = color
        )
    }

    fun addNote(uid: String, title: String, description: String, label: String, color: String) =
        viewModelScope.launch {
            addNoteResponse = Response.Loading
            addNoteResponse = useCases.addNote(
                uid = uid,
                title = title,
                description = description,
                label = label,
                color = color
            )
        }

    fun deleteNote(uid: String, noteId: String) = viewModelScope.launch {
        deleteNoteResponse = Response.Loading
        deleteNoteResponse = useCases.deleteNote(uid, noteId)
    }

}