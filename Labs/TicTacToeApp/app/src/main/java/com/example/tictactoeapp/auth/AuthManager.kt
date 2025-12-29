package com.example.tictactoeapp.auth

import kotlinx.coroutines.flow.StateFlow

interface AuthManager {
    val currentUser: StateFlow<User?>
    suspend fun login(username: String): Boolean
    suspend fun logout()
}
