package com.example.tictactoeapp.ai

import com.example.tictactoeapp.core.GameBoard

/**
 * Стратегия выбора хода для компьютерного игрока.
 */
interface AiStrategy {

    /**
     * Выбирает ход для заданного игрока.
     *
     * @param board текущее состояние поля
     * @param symbol символ игрока ('X' или 'O')
     * @return пара (row, col) или null, если ход невозможен
     */
    fun chooseMove(board: GameBoard, symbol: Char): Pair<Int, Int>?
}
