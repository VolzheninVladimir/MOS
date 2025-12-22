package com.example.tictactoeapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tictactoeapp.ai.ComputerPlayer
import com.example.tictactoeapp.ai.EasyStrategy
import com.example.tictactoeapp.ai.HardStrategy
import com.example.tictactoeapp.ai.MediumStrategy
import com.example.tictactoeapp.core.GameController
import com.example.tictactoeapp.core.GameControllerImpl
import com.example.tictactoeapp.core.HumanPlayer
import com.example.tictactoeapp.ui.Difficulty
import com.example.tictactoeapp.ui.GameState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * ViewModel управляет состоянием игры и связывает ядро с UI.
 */
class GameViewModelImpl(
    private val isComputerGame: Boolean,
    private val difficulty: Difficulty?
) : ViewModel(), GameViewModel {

    private val controller: GameController =
        if (isComputerGame) {
            GameControllerImpl(
                playerX = HumanPlayer('X'),
                playerO = ComputerPlayer(
                    'O',
                    strategy = when (difficulty) {
                        Difficulty.EASY -> EasyStrategy()
                        Difficulty.MEDIUM -> MediumStrategy()
                        Difficulty.HARD -> HardStrategy()
                        else -> EasyStrategy()
                    }
                )
            )
        } else {
            GameControllerImpl(
                playerX = HumanPlayer('X'),
                playerO = HumanPlayer('O')
            )
        }

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

    private fun updateStateAfterMove() {
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

    override fun makeMove(row: Int, col: Int) {
        if (controller.makeMove(row, col)) {
            updateStateAfterMove()
        }
    }

    override fun makeComputerMove() {
        if (controller.makeAIMove()) {
            updateStateAfterMove()
        }
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
