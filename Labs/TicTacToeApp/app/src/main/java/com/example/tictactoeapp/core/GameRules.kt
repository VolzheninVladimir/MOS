package com.example.tictactoeapp.core


/**
 * Отвечает за проверку состояния игрового поля:
 * победа, ничья, допустимость хода.
 */
interface GameRules {

    /**
     * Проверяет, есть ли победитель на поле.
     * @param board текущее состояние поля
     * @return символ победителя ('X' или 'O'), либо null если победителя нет
     */
    fun checkWinner(board: GameBoard): Char?

    /**
     * Проверяет, завершена ли игра ничьей.
     * @param board текущее состояние поля
     * @return true если все клетки заняты и победителя нет
     */
    fun isDraw(board: GameBoard): Boolean

    /**
     * Проверяет, допустим ли ход.
     * @param board текущее состояние поля
     * @param row индекс строки (0..2)
     * @param col индекс столбца (0..2)
     * @return true если клетка свободна и ход возможен
     */
    fun isMoveValid(board: GameBoard, row: Int, col: Int): Boolean
}

/**
 * Реализация правил игры.
 */
class GameRulesImpl : GameRules {

    override fun checkWinner(board: GameBoard): Char? {
        val b = board.getBoardSnapshot()

        for (row in 0..2) {
            if (b[row][0] != null && b[row][0] == b[row][1] && b[row][1] == b[row][2]) {
                return b[row][0]
            }
        }

        for (col in 0..2) {
            if (b[0][col] != null && b[0][col] == b[1][col] && b[1][col] == b[2][col]) {
                return b[0][col]
            }
        }

        if (b[0][0] != null && b[0][0] == b[1][1] && b[1][1] == b[2][2]) return b[0][0]
        if (b[0][2] != null && b[0][2] == b[1][1] && b[1][1] == b[2][0]) return b[0][2]

        return null
    }

    override fun isDraw(board: GameBoard): Boolean {
        val b = board.getBoardSnapshot()
        return b.all { row -> row.all { it != null } } && checkWinner(board) == null
    }

    override fun isMoveValid(board: GameBoard, row: Int, col: Int): Boolean {
        return board.getCell(row, col) == null
    }
}
