package com.example.tictactoeapp.auth

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.tictactoeapp.database.UserDao
import com.example.tictactoeapp.database.UserEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore("auth_prefs")

class AuthManagerImpl(
    private val context: Context,
    private val userDao: UserDao
) : AuthManager {

    private val _currentUser = MutableStateFlow<User?>(null)
    override val currentUser: StateFlow<User?> = _currentUser

    override suspend fun login(username: String): Boolean {
        var user = userDao.getUserByName(username)

        if (user == null) {
            val id = userDao.insertUser(UserEntity(username = username)).toInt()
            user = userDao.getUserByName(username)
        }

        user?.let {
            saveUserId(it.id)
            _currentUser.value = it.toUser()
            return true
        }

        return false
    }

    override suspend fun logout() {
        saveUserId(-1)
        _currentUser.value = null
    }

    suspend fun updateStats(winner: Char) {
        val user = _currentUser.value ?: return

        val updated = when (winner) {
            'X' -> user.copy(wins = user.wins + 1)
            'O' -> user.copy(losses = user.losses + 1)
            'D' -> user.copy(draws = user.draws + 1)
            else -> user
        }

        userDao.updateUser(
            UserEntity(
                id = updated.id,
                username = updated.username,
                wins = updated.wins,
                losses = updated.losses,
                draws = updated.draws,
                lastLogin = updated.lastLogin
            )
        )

        _currentUser.value = updated
    }

    private suspend fun saveUserId(id: Int) {
        context.dataStore.edit { prefs ->
            prefs[AuthPreferences.CURRENT_USER_ID] = id
        }
    }

    private fun UserEntity.toUser() = User(
        id = id,
        username = username,
        wins = wins,
        losses = losses,
        draws = draws,
        lastLogin = lastLogin
    )
}
