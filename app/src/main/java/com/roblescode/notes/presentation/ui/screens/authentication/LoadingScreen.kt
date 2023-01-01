package com.roblescode.notes.presentation.ui.screens.authentication

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.roblescode.notes.presentation.ui.navigation.ROUTE_HOME
import com.roblescode.notes.presentation.ui.navigation.ROUTE_LOADING
import com.roblescode.notes.presentation.ui.navigation.ROUTE_LOGIN

@Composable
fun LoadingScreen(viewModel: AuthViewModel, navController: NavHostController) {



    val currentUser = viewModel.currentUser
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(
                modifier = Modifier.size(50.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Loading...")
        }
    }
    if (currentUser==null){
        navController.navigate(ROUTE_LOGIN){
            popUpTo(ROUTE_LOADING){inclusive = true}
        }
    }else{
        navController.navigate(ROUTE_HOME){
            popUpTo(ROUTE_LOADING){inclusive = true}
        }
    }
}