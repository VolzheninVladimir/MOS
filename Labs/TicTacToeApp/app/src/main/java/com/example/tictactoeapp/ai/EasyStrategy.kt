package com.example.tictactoeapp.ai

import com.example.tictactoeapp.core.GameBoard

/**
 * Простая стратегия:
 * 1. Центр, если свободен.
 * 2. Любой свободный угол.
 * 3. Любое свободное ребро.
 */
class EasyStrategy : AiStrategy {

    override fun chooseMove(board: GameBoard, symbol: Char): Pair<Int, Int>? {
        val snapshot = board.getBoardSnapshot()

        // 1. Центр
        if (snapshot[1][1] == null) {
            return 1 to 1
        }

        // 2. Углы
        val corners = listOf(
            0 to 0,
            0 to 2,
            2 to 0,
            2 to 2
        )
        for ((r, c) in corners) {
            if (snapshot[r][c] == null) {
                return r to c
            }
        }

        // 3. Рёбра
        val edges = listOf(
            0 to 1,
            1 to 0,
            1 to 2,
            2 to 1
        )
        for ((r, c) in edges) {
            if (snapshot[r][c] == null) {
                return r to c
            }
        }

        // Нет доступных ходов
        return null
    }
}
