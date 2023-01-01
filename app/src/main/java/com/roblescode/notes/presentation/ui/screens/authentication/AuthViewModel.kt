package com.roblescode.notes.presentation.ui.screens.authentication

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import com.roblescode.notes.data.auth.AuthRepository
import com.roblescode.notes.data.auth.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _loginFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val loginFlow : StateFlow<Resource<FirebaseUser>?> = _loginFlow

    val currentUser : FirebaseUser?
            get() = repository.currentUser

    init {

        if (repository.currentUser!=null){
            _loginFlow.value = Resource.Success(repository.currentUser!!)
        }
    }

    fun loginWithGoogle(task: Task<GoogleSignInAccount>)=viewModelScope.launch {
        _loginFlow.value = Resource.Loading
        val result = repository.loginWithGoogle(task)
        _loginFlow.value = result
    }

    fun logout(context: Context) {
        repository.logout(context = context)
        _loginFlow.value = null
    }

}