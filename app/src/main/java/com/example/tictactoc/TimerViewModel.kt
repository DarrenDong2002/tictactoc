package com.example.tictactoc

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job

class TimerViewModel : ViewModel() {
    private val _timerValue = MutableLiveData<Long>(10000L)
    val timerValue: LiveData<Long> = _timerValue

    private val _consecutiveWins = MutableLiveData<Int>(0)
    val consecutiveWins: LiveData<Int> = _consecutiveWins

    private val _gameOver = MutableLiveData<Boolean>(false)
    val gameOver: LiveData<Boolean> = _gameOver

    private val _ticTacToeBoard = MutableLiveData<List<String>>(List(9) { "" })
    val ticTacToeBoard: LiveData<List<String>> = _ticTacToeBoard

    private val ticTacToeGame = TicTacToeGame()

    private var timerJob: Job? = null

    init {
        startTimer()
    }

    private fun startTimer() {
        timerJob = viewModelScope.launch {
            while (_timerValue.value!! > 0) {
                delay(1000)
                _timerValue.value = _timerValue.value!! - 1000
            }
            _gameOver.value = true
        }
    }

    fun pauseTimer() {
        timerJob?.cancel()
    }

    fun resumeTimer() {
        if (_timerValue.value!! > 0) {
            startTimer()
        } else {
            resetGame()
        }
    }

    fun resetConsecutiveWins() {
        _consecutiveWins.value = 0
    }

    fun onCellClicked(index: Int) {
        if (ticTacToeGame.board[index].isNotBlank() || ticTacToeGame.gameOver) return

        ticTacToeGame.makeMove(index, "X")
        _ticTacToeBoard.value = ticTacToeGame.board

        if (!ticTacToeGame.gameOver) {
            val botMove = ticTacToeGame.findBestMove("O")
            ticTacToeGame.makeMove(botMove, "O")
            _ticTacToeBoard.value = ticTacToeGame.board
        }

        if (ticTacToeGame.winner == "X") {
            _timerValue.value = _timerValue.value!! + 5000
            _consecutiveWins.value = _consecutiveWins.value!! + 1
            resetBoard()
        } else if (ticTacToeGame.winner == "O" || ticTacToeGame.isBoardFull()) {
            _gameOver.value = true
            resetGame()
        }
    }

    private fun resetBoard() {
        ticTacToeGame.resetBoard()
        _ticTacToeBoard.value = ticTacToeGame.board
    }

    fun resetGame() {
        resetBoard()
        _timerValue.value = 10000L
    }
}