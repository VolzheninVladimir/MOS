package com.example.tictactoeapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tictactoeapp.ai.ComputerPlayer
import com.example.tictactoeapp.ai.EasyStrategy
import com.example.tictactoeapp.ai.HardStrategy
import com.example.tictactoeapp.ai.MediumStrategy
import com.example.tictactoeapp.core.GameController
import com.example.tictactoeapp.core.GameControllerImpl
import com.example.tictactoeapp.core.HumanPlayer
import com.example.tictactoeapp.core.Player
import com.example.tictactoeapp.ui.Difficulty
import com.example.tictactoeapp.ui.GameState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow

class GameViewModelImpl : ViewModel(), GameViewModel {

    private val _gameFinished = MutableSharedFlow<Char>(extraBufferCapacity = 1)
    override val gameFinished = _gameFinished.asSharedFlow()

    private lateinit var controller: GameController

    private val _state = MutableStateFlow(
        GameState(
            board = Array(3) { Array<Char?>(3) { null } },
            currentPlayer = 'X',
            winner = null,
            isDraw = false,
            isGameOver = false
        )
    )
    override val state: StateFlow<GameState> = _state

    override fun startNewGame(playerX: Player, playerO: Player) {
        controller = GameControllerImpl(
            playerX = playerX,
            playerO = playerO
        )

        _state.value = GameState(
            board = controller.board.getBoardSnapshot(),
            currentPlayer = controller.currentPlayer,
            winner = null,
            isDraw = false,
            isGameOver = false
        )
    }

    override fun currentPlayerObject(): Player {
        return if (controller.currentPlayer == 'X') controller.playerX else controller.playerO
    }

    private fun updateStateAfterMove() {
        val winner = controller.checkGameStatus()
        val isDraw = (winner == 'D')

        if (winner != null) {
            _gameFinished.tryEmit(winner)
        }

        _state.value = GameState(
            board = controller.board.getBoardSnapshot(),
            currentPlayer = controller.currentPlayer,
            winner = if (winner != null && winner != 'D') winner else null,
            isDraw = isDraw,
            isGameOver = winner != null
        )
    }

    override fun makeMove(row: Int, col: Int) {
        if (state.value.isGameOver) return
        if (controller.makeMove(row, col)) updateStateAfterMove()
    }

    override fun makeAIMove() {
        if (state.value.isGameOver) return
        if (controller.makeAIMove(controller.currentPlayer)) updateStateAfterMove()
    }

    override fun resetGame() {
        controller.resetGame()
        _state.value = GameState(
            board = controller.board.getBoardSnapshot(),
            currentPlayer = controller.currentPlayer,
            winner = null,
            isDraw = false,
            isGameOver = false
        )
    }
}
