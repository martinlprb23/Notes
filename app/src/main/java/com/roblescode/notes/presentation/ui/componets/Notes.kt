package com.roblescode.notes.presentation.ui.componets

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.roblescode.notes.data.firestore.model.Response
import com.roblescode.notes.data.firestore.repository.Notes
import com.roblescode.notes.presentation.ui.screens.home.NotesViewModel

@Composable
fun Notes(
    uid : String,
    viewModel: NotesViewModel = hiltViewModel(),
    notesContent: @Composable (notes: Notes) -> Unit
) {
    viewModel.getNotes(uid)
    when (val notesResponse = viewModel.notesResponse) {
        is Response.Loading -> Loading()
        is Response.Success -> notesContent(notesResponse.data)
        is Response.Failure -> print(notesResponse.e)
    }
}