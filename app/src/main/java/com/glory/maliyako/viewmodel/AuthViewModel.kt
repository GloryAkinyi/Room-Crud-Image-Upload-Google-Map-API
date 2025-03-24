package com.glory.maliyako.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glory.maliyako.model.User
import com.glory.maliyako.repository.UserRepository
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: UserRepository) : ViewModel() {
    var loginSuccess: ((Boolean) -> Unit)? = null

    fun registerUser(user: User) {
        viewModelScope.launch {
            repository.registerUser(user)
        }
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            val user = repository.loginUser(email, password)
            loginSuccess?.invoke(user != null)
        }
    }
}