package com.example.tictactoeapp.auth

data class User(
    val id: Int,
    val username: String,
    val wins: Int,
    val losses: Int,
    val draws: Int,
    val lastLogin: Long
)
