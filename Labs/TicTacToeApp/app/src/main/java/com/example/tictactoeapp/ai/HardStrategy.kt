package com.example.tictactoeapp.ai

import com.example.tictactoeapp.core.GameBoard
import com.example.tictactoeapp.core.GameRulesImpl

class HardStrategy : AiStrategy {

    private val rules = GameRulesImpl()

    override fun chooseMove(board: GameBoard, symbol: Char): Pair<Int, Int>? {
        val snapshot = board.getBoardSnapshot()
        var bestScore = Int.MIN_VALUE
        var bestMove: Pair<Int, Int>? = null

        for (row in 0..2) {
            for (col in 0..2) {
                if (snapshot[row][col] == null) {
                    snapshot[row][col] = symbol
                    val score = minimax(snapshot, false, symbol)
                    snapshot[row][col] = null

                    if (score > bestScore) {
                        bestScore = score
                        bestMove = row to col
                    }
                }
            }
        }

        return bestMove
    }

    private fun minimax(
        board: Array<Array<Char?>>,
        isMaximizing: Boolean,
        aiSymbol: Char
    ): Int {
        val opponent = if (aiSymbol == 'X') 'O' else 'X'
        val winner = checkWinner(board)

        if (winner == aiSymbol) return 10
        if (winner == opponent) return -10
        if (isBoardFull(board)) return 0

        if (isMaximizing) {
            var bestScore = Int.MIN_VALUE
            for (r in 0..2) {
                for (c in 0..2) {
                    if (board[r][c] == null) {
                        board[r][c] = aiSymbol
                        val score = minimax(board, false, aiSymbol)
                        board[r][c] = null
                        bestScore = maxOf(bestScore, score)
                    }
                }
            }
            return bestScore
        } else {
            var bestScore = Int.MAX_VALUE
            for (r in 0..2) {
                for (c in 0..2) {
                    if (board[r][c] == null) {
                        board[r][c] = opponent
                        val score = minimax(board, true, aiSymbol)
                        board[r][c] = null
                        bestScore = minOf(bestScore, score)
                    }
                }
            }
            return bestScore
        }
    }

    private fun checkWinner(board: Array<Array<Char?>>): Char? {
        val rules = GameRulesImpl()
        return rules.checkWinner(object : GameBoard {
            override fun getCell(r: Int, c: Int) = board[r][c]
            override fun setCell(r: Int, c: Int, s: Char) = false
            override fun reset() {}
            override fun getBoardSnapshot() = board
        })
    }

    private fun isBoardFull(board: Array<Array<Char?>>): Boolean {
        return board.all { row -> row.all { it != null } }
    }
}
