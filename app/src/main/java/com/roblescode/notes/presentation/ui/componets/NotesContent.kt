package com.roblescode.notes.presentation.ui.componets


import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.roblescode.notes.data.firestore.model.Note
import com.roblescode.notes.data.firestore.repository.Notes
import com.roblescode.notes.presentation.theme.*
import com.roblescode.notes.presentation.ui.navigation.ROUTE_ADD_NOTE
import com.roblescode.notes.presentation.ui.navigation.ROUTE_HOME
import java.time.format.DateTimeFormatter
import java.util.*


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotesContent(notes: Notes, navController: NavHostController) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(notes) {
            CardNote(note = it, navController = navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardNote(
    note: Note,
    navController: NavHostController
) {

    ElevatedCard(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 10.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        onClick = {
            navController.navigate("$ROUTE_ADD_NOTE/${true}/${note.id}") {
                popUpTo(ROUTE_HOME) { inclusive = false }
            }
        },
        colors = CardDefaults.cardColors(
            containerColor =
            when (note.color) {
                "blue" -> BlueColor
                "dark" -> DarkBlack
                "yellow" -> YellowColor
                "pink" -> PinkColor
                "green" -> GreenColor
                "orange" -> OrangeColor
                else -> MaterialTheme.colorScheme.surface
            }
        )
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = note.title.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .heightIn(100.dp, 200.dp)
                    .padding(bottom = 16.dp)
            ) {
                Text(
                    text = note.description.toString(),
                    fontSize = 12.sp,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .shadow(0.dp, RoundedCornerShape(10.dp))
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.surface,
                            RoundedCornerShape(10.dp)
                        )
                ) {
                    Text(
                        text = if (note.label == null || note.label == "Nothing") "Nil" else note.label.toString(),
                        fontSize = 10.sp,
                        modifier = Modifier
                            .padding(6.dp)
                    )
                }

                    Text(
                        text = note.date.toString().take(10),
                        fontSize = 10.sp
                    )

            }
        }
    }
}

