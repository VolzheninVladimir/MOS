package com.example.tictactoeapp.core


/**
 * Представляет игровое поле 3×3.
 * Отвечает за хранение состояния клеток и базовые операции над ними.
 */
interface GameBoard {

    /**
     * Возвращает символ в указанной клетке.
     * @param row индекс строки (0..2)
     * @param col индекс столбца (0..2)
     * @return символ клетки: 'X', 'O' или null
     */
    fun getCell(row: Int, col: Int): Char?

    /**
     * Устанавливает символ в указанную клетку.
     * @param row индекс строки (0..2)
     * @param col индекс столбца (0..2)
     * @param symbol символ игрока ('X' или 'O')
     * @return true если ход успешен, false если клетка занята
     */
    fun setCell(row: Int, col: Int, symbol: Char): Boolean

    /**
     * Сброс поля до начального состояния.
     */
    fun reset()

    /**
     * Возвращает копию текущего состояния поля.
     * @return двумерный массив 3×3
     */
    fun getBoardSnapshot(): Array<Array<Char?>>
}

class GameBoardImpl : GameBoard {

    private val board: Array<Array<Char?>> = Array(3) { arrayOfNulls<Char?>(3) }

    override fun getCell(row: Int, col: Int): Char? = board[row][col]

    override fun setCell(row: Int, col: Int, symbol: Char): Boolean {
        if (board[row][col] == null) {
            board[row][col] = symbol
            return true
        }
        return false
    }

    override fun reset() {
        for (row in 0..2) {
            for (col in 0..2) {
                board[row][col] = null
            }
        }
    }

    override fun getBoardSnapshot(): Array<Array<Char?>> {
        return board.map { it.copyOf() }.toTypedArray()
    }
}
