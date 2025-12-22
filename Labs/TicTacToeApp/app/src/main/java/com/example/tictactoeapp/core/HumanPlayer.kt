package com.example.tictactoeapp.core

class HumanPlayer(
    override val symbol: Char,
    override val name: String = "Human"
) : Player {
    override fun makeMove(board: GameBoard): Pair<Int, Int> {
        error("HumanPlayer.makeMove() should not be called directly")
    }
}
