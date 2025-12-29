package com.example.tictactoeapp.leaderboard

import com.example.tictactoeapp.auth.User
import com.example.tictactoeapp.database.UserDao
import com.example.tictactoeapp.database.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LeaderboardRepository(
    private val userDao: UserDao
) {

    fun loadLeaderboard(): Flow<List<User>> {
        return userDao.getLeaderboard().map { list ->
            list.map { it.toUser() }
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

