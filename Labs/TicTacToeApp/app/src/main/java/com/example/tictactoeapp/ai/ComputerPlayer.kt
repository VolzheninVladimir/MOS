package com.example.tictactoeapp.ai

import com.example.tictactoeapp.core.GameBoard
import com.example.tictactoeapp.core.Player

/**
 * Компьютерный игрок, использующий стратегию AI.
 */
class ComputerPlayer(
    override val symbol: Char,
    override val name: String = "Computer",
    private val strategy: AiStrategy
) : Player {

    override fun makeMove(board: GameBoard): Pair<Int, Int> {
        return strategy.chooseMove(board, symbol) ?: (-1 to -1)
    }
}
