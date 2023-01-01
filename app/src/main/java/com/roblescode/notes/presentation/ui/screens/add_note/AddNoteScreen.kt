package com.roblescode.notes.presentation.ui.screens.add_note

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.roblescode.notes.R
import com.roblescode.notes.data.firestore.model.Response
import com.roblescode.notes.presentation.theme.*
import com.roblescode.notes.presentation.ui.componets.*
import com.roblescode.notes.presentation.ui.navigation.ROUTE_HOME
import com.roblescode.notes.presentation.ui.screens.authentication.AuthViewModel
import com.roblescode.notes.presentation.ui.screens.home.NotesViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(
    update: Boolean,
    noteId: String?,
    viewModel: AuthViewModel,
    navController: NavHostController,
    viewModelNotes: NotesViewModel = hiltViewModel()
) {
    var description by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var getComplete by remember { mutableStateOf(false) }
    var selectColor by remember { mutableStateOf(Color.Unspecified) }
    val userUid by remember {
        mutableStateOf(viewModel.currentUser?.uid)
    }


    if (update) {
        userUid?.let {
            if (noteId != null) {
                viewModelNotes.getNoteById(uid = it, docId = noteId)
                //Toast.makeText(LocalContext.current, viewModelNotes.noteResponse.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    var expanded by remember { mutableStateOf(false) }
    val mCities = listOf("Design", "Leisure", "Personal", "Travel", "Task", "Work", "Nothing")
    var selectedLabel by remember { mutableStateOf("Nothing") }

    val formatter = DateTimeFormatter.ofPattern("hh:mm a")
    var currentDay = LocalDateTime.now().dayOfWeek.toString().lowercase()
    currentDay =
        currentDay.uppercase()[0] + currentDay.substring(1, currentDay.length).lowercase()
    val currentHour = LocalDateTime.now().format(formatter)

    Scaffold(
        topBar = {
        TopAppBar(title = {
            Text(
                text = if (update) "My note" else "Add note",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        },
            modifier = Modifier.shadow(elevation = 2.dp),
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = null
                    )
                }
            },
            actions = {
                IconButton(
                    onClick = {
                        if (update) {
                            viewModelNotes.updateNote(
                                uid = viewModel.currentUser?.uid.toString(),
                                noteId = noteId.toString(),
                                title = title,
                                description = description,
                                label = selectedLabel,
                                color = when (selectColor) {
                                    BlueColor -> "blue"
                                    YellowColor -> "yellow"
                                    DarkBlack -> "dark"
                                    GreenColor -> "green"
                                    PinkColor -> "pink"
                                    OrangeColor -> "orange"
                                    else -> "default"
                                }
                            )

                        } else {
                            viewModelNotes.addNote(
                                uid = viewModel.currentUser?.uid.toString(),
                                title = title.ifEmpty { description.substringBefore(" ").take(50) },
                                description = description,
                                label = selectedLabel,
                                color = when (selectColor) {
                                    BlueColor -> "blue"
                                    YellowColor -> "yellow"
                                    DarkBlack -> "dark"
                                    GreenColor -> "green"
                                    PinkColor -> "pink"
                                    OrangeColor -> "orange"
                                    else -> "default"
                                }
                            )
                        }
                        navController.popBackStack(route = ROUTE_HOME, inclusive = false, saveState = false)
                    }, enabled = (title.isNotEmpty() || description.isNotEmpty())
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.check),
                        contentDescription = "Add note icon"
                    )
                }
            })
    },
        content = { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = title,
                onValueChange = {
                    if (it.length <= 50) {
                        title = it
                    }
                },
                placeholder = {
                    Text(
                        text = "Title", fontWeight = FontWeight.Bold, fontSize = 20.sp
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                trailingIcon = {
                    Box(modifier = Modifier.padding(end = 8.dp)) {
                        IconButton(onClick = {
                            expanded = true
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.label),
                                contentDescription = null
                            )
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            mCities.forEach { label ->
                                DropdownMenuItem(
                                    onClick = {
                                        selectedLabel = label
                                        expanded = false
                                    }, text = { Text(text = label) })
                            }
                        }
                    }
                },
                textStyle = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "$currentDay $currentHour | $selectedLabel",
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
            TextField(
                value = description,
                onValueChange = {
                    description = it
                },
                placeholder = { Text(text = "Description") },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
            AddNote()
            UpdateNote()
            DeleteNote()
        }

        if (update && !getComplete) {
            when (val noteResponse = viewModelNotes.noteResponse) {
                is Response.Loading -> Loading()
                is Response.Success -> {
                    description = noteResponse.data?.description.toString()
                    title = noteResponse.data?.title.toString()
                    selectColor = when (noteResponse.data?.color) {
                        "blue" -> BlueColor
                        "yellow" -> YellowColor
                        "dark" -> DarkBlack
                        "green" -> GreenColor
                        "pink" -> PinkColor
                        "orange" -> OrangeColor
                        else -> MaterialTheme.colorScheme.surface
                    }
                    selectedLabel = noteResponse.data?.label.toString()
                    getComplete = true
                }
                is Response.Failure -> print(noteResponse.e)
            }
        }

    },
        bottomBar = {
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            ),
            shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
            colors = CardDefaults.cardColors(
                containerColor = DarkBlack, contentColor = Color.White
            )
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Column(horizontalAlignment = Alignment.Start, modifier = Modifier.padding(16.dp)) {
                SelectColors(selectColor) {
                    selectColor = it
                }
                Spacer(modifier = Modifier.height(16.dp))
                if (update) {
                    ActionButtons(
                        navController = navController,
                        authViewModel = viewModel,
                        notesViewModel = viewModelNotes,
                        title = title,
                        description = description,
                        noteId = noteId.toString()
                    )
                }

                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.time),
                        contentDescription = null
                    )
                    Text(
                        text = "$currentDay $currentHour | ${description.length} characters",
                        fontSize = 14.sp
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.points),
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    })

}


@Composable
fun ActionButtons(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    notesViewModel: NotesViewModel,
    noteId: String,
    title: String,
    description: String,
) {
    var showDialog by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "$title\n\n$description")
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    val clipboardManager = LocalClipboardManager.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
                showDialog = true
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.delete),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = "Delete note")
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
                clipboardManager.setText(AnnotatedString("$title\n\n$description"))
                Toast
                    .makeText(context, "Coped note", Toast.LENGTH_SHORT)
                    .show()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.copy),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = "Make a copy")
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
                context.startActivity(shareIntent)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.share),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = "Share")
    }
    Spacer(modifier = Modifier.height(16.dp))
    if (showDialog) {
        ConfirmDialog(onConfirmClicked = {
            notesViewModel.deleteNote(
                uid = authViewModel.currentUser?.uid.toString(),
                noteId = noteId
            )
            showDialog = false
            navController.popBackStack(route = ROUTE_HOME, inclusive = false, saveState = false)
        }) {
            showDialog = false
        }
    }
}


@Composable
fun SelectColors(selectColor: Color, onSelected: (Color) -> Unit = {}) {
    val colorList: List<Color> = listOf(
        BlueColor, YellowColor, PinkColor, GreenColor, OrangeColor, DarkBlack
    )
    LazyRow(
        horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
    ) {
        this.items(colorList) { color ->
            Box(
                modifier = Modifier
                    .size(35.dp)
                    .shadow(1.dp, shape = RoundedCornerShape(50))
                    .border(4.dp, TransparentGray, RoundedCornerShape(50))
                    .background(color)
                    .clickable {
                        onSelected(color)
                    }, contentAlignment = Alignment.Center
            ) {
                if (color == selectColor) {
                    Icon(
                        painter = painterResource(id = R.drawable.ok),
                        contentDescription = null,
                        tint = if (color == DarkBlack) Color.White else MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

    }
}
