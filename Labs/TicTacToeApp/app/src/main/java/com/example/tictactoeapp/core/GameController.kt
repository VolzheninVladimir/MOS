package com.example.tictactoeapp.core

import com.example.tictactoeapp.ai.ComputerPlayer
import com.example.tictactoeapp.core.GameBoard
import com.example.tictactoeapp.core.GameBoardImpl

/**
 * Управляет процессом игры:
 * - переключает игроков
 * - выполняет ходы
 * - проверяет завершение партии
 */
interface GameController {

    /**
     * Текущий игрок ('X' или 'O').
     */
    val currentPlayer: Char

    /**
     * Игровое поле.
     */
    val board: GameBoard

    val playerX: Player
    val playerO: Player

    /**
     * Делает ход текущего игрока.
     * @param row индекс строки (0..2)
     * @param col индекс столбца (0..2)
     * @return true если ход успешен, false если клетка занята или игра завершена
     */
    fun makeMove(row: Int, col: Int): Boolean

    fun makeAIMove(): Boolean

    /**
     * Проверяет состояние игры после хода.
     * @return символ победителя ('X' или 'O'),
     *         "D" если ничья,
     *         null если игра продолжается
     */
    fun checkGameStatus(): Char?

    /**
     * Переключает ход на следующего игрока.
     */
    fun switchPlayer()

    /**
     * Сбрасывает игру до начального состояния.
     */
    fun resetGame()
}


class GameControllerImpl(
    override val board: GameBoard = GameBoardImpl(),
    private val rules: GameRules = GameRulesImpl(),
    override val playerX: Player = HumanPlayer('X'),
    override val playerO: Player = HumanPlayer('O')
) : GameController {

    override var currentPlayer: Char = 'X'
        private set

    override fun makeMove(row: Int, col: Int): Boolean {
        if (!rules.isMoveValid(board, row, col)) return false

        val success = board.setCell(row, col, currentPlayer)
        if (success) switchPlayer()
        return success
    }


    override fun checkGameStatus(): Char? {
        val winner = rules.checkWinner(board)
        if (winner != null) return winner
        if (rules.isDraw(board)) return 'D'
        return null
    }

    override fun makeAIMove(): Boolean {
        val player = if (currentPlayer == 'X') playerX else playerO

        // Если игрок — не компьютер, AI ход невозможен
        if (player !is ComputerPlayer) return false

        val (row, col) = player.makeMove(board)

        // Если стратегия вернула (-1, -1) — нет хода
        if (row !in 0..2 || col !in 0..2) return false

        val success = board.setCell(row, col, currentPlayer)
        if (success) switchPlayer()
        return success
    }

    override fun switchPlayer() {
        currentPlayer = if (currentPlayer == 'X') 'O' else 'X'
    }

    override fun resetGame() {
        board.reset()
        currentPlayer = 'X'
    }
}


