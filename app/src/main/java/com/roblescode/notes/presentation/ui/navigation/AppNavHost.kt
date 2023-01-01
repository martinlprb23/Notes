package com.roblescode.notes.presentation.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.roblescode.notes.presentation.ui.screens.add_note.AddNoteScreen
import com.roblescode.notes.presentation.ui.screens.authentication.AuthViewModel
import com.roblescode.notes.presentation.ui.screens.authentication.AuthenticationScreen
import com.roblescode.notes.presentation.ui.screens.authentication.LoadingScreen
import com.roblescode.notes.presentation.ui.screens.home.HomeScreen
import com.roblescode.notes.presentation.ui.screens.profile.ProfileScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(
    viewModel: AuthViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUTE_LOADING
) {
    NavHost(
        modifier = modifier.fillMaxSize(),
        navController = navController,
        startDestination = startDestination
    ) {
        composable(ROUTE_LOADING) {
            LoadingScreen(viewModel, navController = navController)
        }
        composable(ROUTE_LOGIN) {
            AuthenticationScreen(viewModel, navController = navController)
        }
        composable(ROUTE_HOME) {
            HomeScreen(viewModel = viewModel, navController = navController)
        }
        composable(ROUTE_PROFILE) {
            ProfileScreen(viewModel, navController)
        }
        composable(
            route = "$ROUTE_ADD_NOTE/{update}/{noteId}",
            arguments = listOf(
                navArgument("update") {
                    type = NavType.BoolType
                },
                navArgument("noteId") {
                    type = NavType.StringType
                }
            )
        ) {
            val update = remember {
                it.arguments?.getBoolean("update")
            }
            val note = remember {
                it.arguments?.getString("noteId")
            }

            if (note != null) {
                if (update != null) {
                    AddNoteScreen(
                        update = update,
                        noteId = note,
                        viewModel = viewModel,
                        navController = navController
                    )
                }
            }
        }
    }
}