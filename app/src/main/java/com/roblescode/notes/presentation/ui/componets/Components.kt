package com.roblescode.notes.presentation.ui.componets

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.roblescode.notes.data.firestore.model.Response
import com.roblescode.notes.presentation.ui.screens.home.NotesViewModel

@Composable
fun Loading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun AddNote(
    viewModel: NotesViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    when (val addNoteResponse = viewModel.addNoteResponse) {
        is Response.Loading -> Loading()
        is Response.Success -> Unit
        is Response.Failure -> Toast.makeText(context, addNoteResponse.e.toString(), Toast.LENGTH_LONG).show()
    }
}

@Composable
fun UpdateNote(
    viewModel: NotesViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    when (val addNoteResponse = viewModel.updateNoteResponse) {
        is Response.Loading -> CircularProgressIndicator()
        is Response.Success ->  Unit
        is Response.Failure -> Toast.makeText(context, addNoteResponse.e.toString(), Toast.LENGTH_LONG).show()
    }
}

@Composable
fun DeleteNote(
    viewModel: NotesViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    when(val deleteNoteResponse = viewModel.deleteNoteResponse) {
        is Response.Loading -> CircularProgressIndicator()
        is Response.Success -> Unit
        is Response.Failure -> Toast.makeText(context, deleteNoteResponse.e.toString(), Toast.LENGTH_LONG).show()
    }
}