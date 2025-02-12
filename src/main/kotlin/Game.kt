// File: Game.kt
// Author: Anointed Omorogbe
// Student ID: 2305044

enum class CellState(val symbol: Char) {
    EMPTY(' '),
    PLAYER_X('X'),
    PLAYER_O('O');

    override fun toString(): String = symbol.toString()
}

class Game {
    private val board: Array<Array<CellState>> = Array(6) { Array(7) { CellState.EMPTY } }
    private var currentPlayer: CellState = CellState.PLAYER_X

    // Display the game board
    fun displayBoard() {
        println("\n 0 1 2 3 4 5 6")
        println("---------------")
        for (row in board) {
            print("|")
            for (cell in row) {
                print("$cell|")
            }
            println("\n---------------")
        }
    }

    // Make a move in the specified column
    fun makeMove(column: Int): Boolean {
        if (column !in 0..6) return false // Invalid column
        for (row in 5 downTo 0) {
            if (board[row][column] == CellState.EMPTY) {
                board[row][column] = currentPlayer
                return true
            }
        }
        return false // Column is full
    }

    // Check for a win condition
    fun checkWin(): Boolean {
        // Check horizontal, vertical, and diagonal wins
        for (row in 0..5) {
            for (col in 0..3) {
                if (board[row][col] != CellState.EMPTY &&
                    board[row][col] == board[row][col + 1] &&
                    board[row][col] == board[row][col + 2] &&
                    board[row][col] == board[row][col + 3]) {
                    return true
                }
            }
        }

        // Check vertical wins
        for (row in 0..2) {
            for (col in 0..6) {
                if (board[row][col] != CellState.EMPTY &&
                    board[row][col] == board[row + 1][col] &&
                    board[row][col] == board[row + 2][col] &&
                    board[row][col] == board[row + 3][col]) {
                    return true
                }
            }
        }

        // Check diagonal (ascending) wins
        for (row in 3..5) {
            for (col in 0..3) {
                if (board[row][col] != CellState.EMPTY &&
                    board[row][col] == board[row - 1][col + 1] &&
                    board[row][col] == board[row - 2][col + 2] &&
                    board[row][col] == board[row - 3][col + 3]) {
                    return true
                }
            }
        }

        // Check diagonal (descending) wins
        for (row in 0..2) {
            for (col in 0..3) {
                if (board[row][col] != CellState.EMPTY &&
                    board[row][col] == board[row + 1][col + 1] &&
                    board[row][col] == board[row + 2][col + 2] &&
                    board[row][col] == board[row + 3][col + 3]) {
                    return true
                }
            }
        }

        return false
    }

    // Check if the board is full
    fun isBoardFull(): Boolean {
        for (row in board) {
            for (cell in row) {
                if (cell == CellState.EMPTY) return false
            }
        }
        return true
    }

    // Switch players
    fun switchPlayer() {
        currentPlayer = if (currentPlayer == CellState.PLAYER_X) CellState.PLAYER_O else CellState.PLAYER_X
    }

    // Get the current player
    fun getCurrentPlayer(): CellState {
        return currentPlayer
    }

    // Main game loop
    fun play() {
        var gameOver = false
        while (!gameOver) {
            displayBoard()
            println("Player ${currentPlayer.symbol}'s turn. Enter column (0-6):")
            val input = readLine()?.toIntOrNull()
            if (input != null && makeMove(input)) {
                if (checkWin()) {
                    displayBoard()
                    println("Player ${currentPlayer.symbol} wins!")
                    gameOver = true
                } else if (isBoardFull()) {
                    displayBoard()
                    println("It's a draw!")
                    gameOver = true
                } else {
                    switchPlayer()
                }
            } else {
                println("Invalid move. Try again.")
            }
        }

        println("Play again? (y/n)")
        val playAgain = readLine()?.lowercase()
        if (playAgain == "y") {
            val newGame = Game()
            newGame.play()
        } else {
            println("Thanks for playing!")
        }
    }
}