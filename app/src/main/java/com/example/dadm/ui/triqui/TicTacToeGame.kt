package com.example.dadm.ui.triqui

class TicTacToeGame {

    companion object {
        const val HUMAN_PLAYER = 'X'
        const val COMPUTER_PLAYER = 'O'
        const val OPEN_SPOT = ' '
        const val BOARD_SIZE = 9
    }

    enum class DifficultyLevel { Easy, Harder, Expert }

    private var board = CharArray(BOARD_SIZE) { OPEN_SPOT }
    private var mDifficultyLevel = DifficultyLevel.Expert

    // Getters y setters de dificultad
    fun getDifficultyLevel(): DifficultyLevel = mDifficultyLevel
    fun setDifficultyLevel(level: DifficultyLevel) { mDifficultyLevel = level }

    fun clearBoard() {
        for (i in board.indices) board[i] = OPEN_SPOT
    }

    /** Devuelve el valor de una celda (HUMAN_PLAYER, COMPUTER_PLAYER o OPEN_SPOT) */
    fun getBoardOccupant(position: Int): Char = board[position]

    /** Devuelve una copia del estado actual del tablero (para guardar en Bundle/persistencia) */
    fun getBoardState(): CharArray = board.clone()

    /** Restaura el estado del tablero desde un CharArray (usado al restaurar instancia) */
    fun setBoardState(newBoard: CharArray?) {
        if (newBoard != null && newBoard.size == BOARD_SIZE)
            board = newBoard.clone()
    }

    /** Intenta hacer un movimiento en la posición dada para el jugador. Solo si está vacía. */
    fun setMove(player: Char, location: Int) {
        if (board[location] == OPEN_SPOT) board[location] = player
    }

    /** Obtiene el movimiento del computador según la dificultad actual. */
    fun getComputerMove(): Int {
        return when (mDifficultyLevel) {
            DifficultyLevel.Easy -> getRandomMove()
            DifficultyLevel.Harder -> getWinningMove() ?: getRandomMove()
            DifficultyLevel.Expert -> getWinningMove() ?: getBlockingMove() ?: getRandomMove()
        }
    }

    // Easy: Siempre aleatorio
    private fun getRandomMove(): Int {
        val available = board.indices.filter { board[it] == OPEN_SPOT }
        return if (available.isNotEmpty()) available.random() else 0
    }

    // Harder y Expert: Busca ganar si puede
    private fun getWinningMove(): Int? {
        for (i in board.indices) {
            if (board[i] == OPEN_SPOT) {
                board[i] = COMPUTER_PLAYER
                if (checkForWinner() == 3) {
                    board[i] = OPEN_SPOT
                    return i
                }
                board[i] = OPEN_SPOT
            }
        }
        return null
    }

    // Expert: Bloquear al humano si está a punto de ganar
    private fun getBlockingMove(): Int? {
        for (i in board.indices) {
            if (board[i] == OPEN_SPOT) {
                board[i] = HUMAN_PLAYER
                if (checkForWinner() == 2) {
                    board[i] = OPEN_SPOT
                    return i
                }
                board[i] = OPEN_SPOT
            }
        }
        return null
    }

    /** 0 = nadie, 1 = empate, 2 = humano, 3 = computadora */
    fun checkForWinner(): Int {
        val winCombos = arrayOf(
            intArrayOf(0, 1, 2), intArrayOf(3, 4, 5), intArrayOf(6, 7, 8),
            intArrayOf(0, 3, 6), intArrayOf(1, 4, 7), intArrayOf(2, 5, 8),
            intArrayOf(0, 4, 8), intArrayOf(2, 4, 6)
        )
        for (combo in winCombos) {
            if (board[combo[0]] != OPEN_SPOT &&
                board[combo[0]] == board[combo[1]] &&
                board[combo[1]] == board[combo[2]]
            ) {
                return if (board[combo[0]] == HUMAN_PLAYER) 2 else 3
            }
        }
        return if (board.all { it != OPEN_SPOT }) 1 else 0
    }
}