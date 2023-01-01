package com.roblescode.notes.presentation.ui.screens.authentication

import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.roblescode.notes.R
import com.roblescode.notes.data.auth.Resource
import com.roblescode.notes.presentation.ui.navigation.ROUTE_HOME
import com.roblescode.notes.presentation.ui.navigation.ROUTE_LOGIN

@Composable
fun AuthenticationScreen(
    viewModel: AuthViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val loginFlow = viewModel.loginFlow.collectAsState()
    val context = LocalContext.current
    val launcher = rememberFirebaseAuthLauncher(viewModel = viewModel)
    val token = stringResource(R.string.default_web_client_id)

    Box(
        modifier = modifier
            .padding(32.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        LazyColumn {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.login),
                        contentDescription = null,
                        modifier = Modifier.size(200.dp)
                    )
                }
            }
            item {
                Column(
                    Modifier
                        .fillMaxWidth()
                ) {
                    Text(text = "Welcome", fontSize = 40.sp)
                    Row {
                        Text(text = "to", fontSize = 40.sp)
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(text = "Notes", fontWeight = FontWeight.Bold, fontSize = 40.sp)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Login or register",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Button(
                        onClick = {
                            val gso =
                                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                    .requestIdToken(token)
                                    .requestEmail()
                                    .build()
                            val googleSignInClient = GoogleSignIn.getClient(context, gso)
                            launcher.launch(googleSignInClient.signInIntent)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.google),
                            contentDescription = "Google icon",
                            tint = Color.Unspecified
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Google")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Created by Robles",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        }
        loginFlow.value?.let {
            when (it) {
                is Resource.Success -> {
                    LaunchedEffect(Unit) {
                        navController.navigate(ROUTE_HOME) {
                            popUpTo(ROUTE_LOGIN) { inclusive = true }
                        }
                    }
                }
                is Resource.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 1.dp)
                }
                is Resource.Failure -> {
                    Toast.makeText(context, it.exception.message.toString() , Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}


@Composable
fun rememberFirebaseAuthLauncher(
    viewModel: AuthViewModel
): ManagedActivityResultLauncher<Intent, ActivityResult> {
    return rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        viewModel.loginWithGoogle(task)
    }
}
