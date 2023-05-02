package com.example.tictactoc
import com.example.tictactoc.updateStatus
import com.google.firebase.auth.FirebaseAuth

private lateinit var firebaseAuth: FirebaseAuth
class TicTacToeGame {
    var board = MutableList(9) { "" }
    var winner: String? = null
    var gameOver = false

    fun makeMove(index: Int, player: String) {
        if (board[index].isBlank()) {
            board[index] = player
            checkWinCondition(player)
        }
    }

    fun findBestMove(player: String): Int {
        // simple bot that makes a random move
        // ad more advanced bot if wanted
        val emptyCells = board.mapIndexed { index, cell -> if (cell.isBlank()) index else null }
            .filterNotNull()
        return emptyCells.random()
    }

    private fun checkWinCondition(player: String) {
        val winningCombinations = arrayOf(
            intArrayOf(0, 1, 2),
            intArrayOf(3, 4, 5),
            intArrayOf(6, 7, 8),
            intArrayOf(0, 3, 6),
            intArrayOf(1, 4, 7),
            intArrayOf(2, 5, 8),
            intArrayOf(0, 4, 8),
            intArrayOf(2, 4, 6)
        )

        for (combination in winningCombinations) {
            if (board[combination[0]] == player &&
                board[combination[1]] == player &&
                board[combination[2]] == player
            ) {
                val uSA = updateStatus()
                firebaseAuth.uid?.let { uSA.updateScore(it) }
                winner = player
                gameOver = true
                break
            }
        }

        if (!gameOver && isBoardFull()) {
            gameOver = true
        }
    }

    fun isBoardFull(): Boolean {
        return board.none { it.isBlank() }
    }

    fun resetBoard() {
        board.fill("")
        winner = null
        gameOver = false
    }
}