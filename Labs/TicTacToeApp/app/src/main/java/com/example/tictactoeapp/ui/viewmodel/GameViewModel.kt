package com.example.tictactoeapp.ui.viewmodel

import com.example.tictactoeapp.core.Player
import com.example.tictactoeapp.ui.GameState
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface GameViewModel {
    val state: StateFlow<GameState>
    val gameFinished: SharedFlow<Char>

    fun startNewGame(playerX: Player, playerO: Player)
    fun makeMove(row: Int, col: Int)
    fun makeAIMove()
    fun resetGame()

    fun currentPlayerObject(): Player
}
