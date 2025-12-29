package com.example.tictactoeapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val username: String,
    val wins: Int = 0,
    val losses: Int = 0,
    val draws: Int = 0,
    val lastLogin: Long = System.currentTimeMillis()
)
