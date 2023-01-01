package com.roblescode.notes.presentation.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.roblescode.notes.R
import com.roblescode.notes.presentation.theme.TransparentGray
import com.roblescode.notes.presentation.ui.componets.Notes
import com.roblescode.notes.presentation.ui.componets.NotesContent
import com.roblescode.notes.presentation.ui.navigation.ROUTE_ADD_NOTE
import com.roblescode.notes.presentation.ui.navigation.ROUTE_HOME
import com.roblescode.notes.presentation.ui.navigation.ROUTE_PROFILE
import com.roblescode.notes.presentation.ui.screens.authentication.AuthViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: AuthViewModel,
    navController: NavHostController
) {
    val currentUser = viewModel.currentUser
    val painter = rememberAsyncImagePainter(model = currentUser?.photoUrl)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Notes", fontWeight = FontWeight.Bold, fontSize = 24.sp) },
                modifier = Modifier.shadow(elevation = 2.dp),
            )
        },
        content = { padding ->
            NotesSection(viewModel, navController, padding, painter)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("$ROUTE_ADD_NOTE/${false}/${null}") {
                        popUpTo(ROUTE_HOME) { inclusive = false }
                    }
                }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_add_24),
                    contentDescription = "Add note"
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NotesSection(
    viewModel: AuthViewModel,
    navController: NavHostController,
    padding: PaddingValues,
    painter: AsyncImagePainter
) {
    var search by remember { mutableStateOf("") }
    Column(
        Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        TextField(
            value = search, onValueChange = {
                search = it
            },
            shape = RoundedCornerShape(50),
            placeholder = { Text(text = "Search your Notes") },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = TransparentGray,
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.note),
                    contentDescription = null
                )
            },
            trailingIcon = {
                Image(
                    painter = painter,
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(35.dp)
                        .shadow(1.dp, RoundedCornerShape(50))
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.onBackground,
                            RoundedCornerShape(50)
                        )
                        .clickable {
                            navController.navigate(ROUTE_PROFILE) {
                                popUpTo(ROUTE_HOME) {
                                    inclusive = false
                                }
                            }
                        }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        Notes(
            uid = viewModel.currentUser?.uid.toString(),
            notesContent = { notes ->
                NotesContent(
                    notes = if (search == "") notes else notes.filter { note ->
                        note.title.toString().startsWith(search)
                    },
                    navController = navController
                )
            }
        )
    }

}

