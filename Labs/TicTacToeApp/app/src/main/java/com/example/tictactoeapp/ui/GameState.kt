package com.example.tictactoeapp.ui

/**
 * Представляет текущее состояние игры для UI.
 * Используется в ViewModel и подписке Compose.
 */
data class GameState(
    val board: Array<Array<Char?>>,
    val currentPlayer: Char,
    val winner: Char?,
    val isDraw: Boolean,
    val isGameOver: Boolean
)
