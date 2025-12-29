package com.example.tictactoeapp.ai

import com.example.tictactoeapp.core.GameBoard
import com.example.tictactoeapp.core.GameRulesImpl

class MediumStrategy : AiStrategy {

    private val rules = GameRulesImpl()

    override fun chooseMove(board: GameBoard, symbol: Char): Pair<Int, Int>? {
        val snapshot = board.getBoardSnapshot()
        val opponent = if (symbol == 'X') 'O' else 'X'

        findWinningMove(snapshot, symbol)?.let { return it }

        findWinningMove(snapshot, opponent)?.let { return it }

        return EasyStrategy().chooseMove(board, symbol)
    }

    private fun findWinningMove(
        board: Array<Array<Char?>>,
        symbol: Char
    ): Pair<Int, Int>? {
        for (row in 0..2) {
            for (col in 0..2) {
                if (board[row][col] == null) {
                    board[row][col] = symbol
                    val winner = GameRulesImpl().checkWinner(
                        object : GameBoard {
                            override fun getCell(r: Int, c: Int) = board[r][c]
                            override fun setCell(r: Int, c: Int, s: Char) = false
                            override fun reset() {}
                            override fun getBoardSnapshot() = board
                        }
                    )
                    board[row][col] = null
                    if (winner == symbol) return row to col
                }
            }
        }
        return null
    }
}
