package com.example.tictactoeapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tictactoeapp.ai.ComputerPlayer
import com.example.tictactoeapp.ai.EasyStrategy
import com.example.tictactoeapp.ai.HardStrategy
import com.example.tictactoeapp.ai.MediumStrategy
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.tictactoeapp.core.GameController
import com.example.tictactoeapp.core.GameControllerImpl
import com.example.tictactoeapp.core.HumanPlayer
import com.example.tictactoeapp.ui.Difficulty
import com.example.tictactoeapp.ui.GameState

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