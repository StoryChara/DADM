package com.example.dadm.ui.triqui

class TicTacToeGame {
    companion object {
        const val HUMAN_PLAYER = 'X'
        const val COMPUTER_PLAYER = 'O'
        const val OPEN_SPOT = ' '
        const val BOARD_SIZE = 9
    }

    private val board = CharArray(BOARD_SIZE) { OPEN_SPOT }
    private val rand = java.util.Random()

    fun clearBoard() {
        for (i in board.indices) board[i] = OPEN_SPOT
    }

    fun setMove(player: Char, location: Int) {
        if (board[location] == OPEN_SPOT) board[location] = player
    }

    fun getComputerMove(): Int {
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
        var move: Int
        do {
            move = rand.nextInt(BOARD_SIZE)
        } while (board[move] != OPEN_SPOT)
        return move
    }

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