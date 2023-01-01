package com.roblescode.notes.presentation.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.roblescode.notes.presentation.ui.navigation.ROUTE_HOME
import com.roblescode.notes.presentation.ui.navigation.ROUTE_LOGIN
import com.roblescode.notes.presentation.ui.screens.authentication.AuthViewModel

@Composable
fun ProfileScreen(viewModel: AuthViewModel, navController: NavHostController) {

    val context = LocalContext.current
    val painter = rememberAsyncImagePainter(model = viewModel.currentUser?.photoUrl)
    val currentUser = viewModel.currentUser

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter =  painter,
                contentDescription = "Profile photo",
                modifier = Modifier
                    .size(100.dp)
                    .shadow(1.dp, RoundedCornerShape(50))
                    .border(1.dp, MaterialTheme.colorScheme.onBackground, RoundedCornerShape(50))
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = currentUser?.displayName.toString())
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = currentUser?.email.toString())
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                viewModel.logout(context)
                navController.navigate(ROUTE_LOGIN) {
                    popUpTo(ROUTE_HOME) {
                        inclusive = true
                    }
                }
            }) {
                Text(text = "Signup")
            }
        }
    }
}