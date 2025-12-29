package com.example.tictactoeapp.settings

import com.example.tictactoeapp.database.UserDao

class SettingsRepository(
    private val userDao: UserDao
) {
    suspend fun resetDatabase() {
        userDao.clearAll()
    }
}
