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
        // Если стратегия не может выбрать ход (нет пустых клеток) — по контракту это ошибка логики,
        // но чтобы не падать, вернём (-1, -1) как "невозможный" ход.
        return strategy.chooseMove(board, symbol) ?: (-1 to -1)
    }
}
