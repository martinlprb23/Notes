package com.roblescode.notes.data.auth

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    val currentUser: FirebaseUser?
    suspend fun loginWithGoogle(task: Task<GoogleSignInAccount>): Resource<FirebaseUser>
    fun logout(context: Context)
}