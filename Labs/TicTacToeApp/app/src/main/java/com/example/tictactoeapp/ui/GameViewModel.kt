package com.example.tictactoeapp.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.tictactoeapp.core.GameController
import com.example.tictactoeapp.core.GameControllerImpl

/**
 * ViewModel управляет состоянием игры и связывает ядро с UI.
 */
interface GameViewModel {

    /**
     * Текущее состояние игры, на которое подписывается UI.
     */
    val state: StateFlow<GameState>

    /**
     * Делает ход игрока (человека).
     * @param row индекс строки (0..2)
     * @param col индекс столбца (0..2)
     */
    fun makeMove(row: Int, col: Int)

    /**
     * Запускает ход компьютера (если текущий игрок — AI).
     */
    fun makeComputerMove()

    /**
     * Сбрасывает игру до начального состояния.
     */
    fun resetGame()
}

/**
 * ViewModel управляет состоянием игры и связывает ядро с UI.
 */
class GameViewModelImpl : ViewModel(), GameViewModel {

    private val controller: GameController = GameControllerImpl()

    private val _state = MutableStateFlow(
        GameState(
            board = controller.board.getBoardSnapshot(),
            currentPlayer = controller.currentPlayer,
            winner = null,
            isDraw = false,
            isGameOver = false
        )
    )
    override val state: StateFlow<GameState> = _state

    override fun makeMove(row: Int, col: Int) {
        val success = controller.makeMove(row, col)
        if (success) {
            val winner = controller.checkGameStatus()
            val isDraw = (winner == 'D')
            _state.value = GameState(
                board = controller.board.getBoardSnapshot(),
                currentPlayer = controller.currentPlayer,
                winner = if (winner != null && winner != 'D') winner else null,
                isDraw = isDraw,
                isGameOver = winner != null
            )
        }
    }

    override fun makeComputerMove() {
        // TODO: AI
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