package tictactoe

import kotlin.math.abs

fun main() {
    val emptyCell = ' '
    var gameState = emptyCell.toString().repeat(9)
    var isGameComplete = false
    var currentPlayer = 'X'

    displayGameBoard(gameState)
    while (!isGameComplete) {
        gameState = readPlayerTurn(gameState, currentPlayer, emptyCell)
        isGameComplete = analyseGameState(gameState, emptyCell)
        currentPlayer = if (currentPlayer == 'O') 'X' else 'O'
    }
}

fun readPlayerTurn(gameState: String, currentPlayer: Char, emptyCell: Char): String {
    var validInput = false
    var newGameState = ""

    while(!validInput) {
        print("Enter the coordinates: ")
        val input = readLine()!!.split(" ")

        when {
            input.size != 2 || input[0].toIntOrNull() == null || input[1].toIntOrNull() == null -> println("You should enter numbers!")
            else -> {
                val xNum = input[1].toInt()
                val yNum = input[0].toInt()

                if (xNum > 3 || yNum > 3) println("Coordinates should be from 1 to 3!")
                else {
                    val pos = (xNum - 1) + (yNum * 3 - 3)
                    val ch = gameState[pos]

                    if (ch == emptyCell) {
                        newGameState = "${gameState.substring(0, pos)}$currentPlayer${gameState.substring(pos + 1)}"
                        validInput = true
                    } else {
                        println("This cell is occupied! Choose another one!")
                    }
                }
            }
        }
    }

    displayGameBoard(newGameState)
    return newGameState
}

fun displayGameBoard(gameState: String) {
    println("---------")
    var str = ""
    for (i in 1..gameState.length) {
        str += " ${gameState[i - 1]}"

        if (i % 3 == 0) {
            println("|$str |")
            str = ""
        }
    }
    println("---------")
}

fun analyseGameState(gameState: String, emptyCell: Char): Boolean {
    val xThreeInRow = checkWinCondition(gameState, 'x')
    val oThreeInRow = checkWinCondition(gameState, 'o')
    val hasEmptyCells = gameState.indexOf(emptyCell) >= 0
    val numXs = countChars(gameState, 'x')
    val numOs = countChars(gameState, 'o')

    // check the gameState and output a message
    when {
        xThreeInRow && oThreeInRow -> println("Impossible")
        abs(numXs - numOs) >= 2 -> println("Impossible")
//        !xThreeInRow && !oThreeInRow && hasEmptyCells -> println("Game not finished")
        !xThreeInRow && !oThreeInRow && !hasEmptyCells -> {
            println("Draw")
            return true
        }
        xThreeInRow && !oThreeInRow -> {
            println("X wins")
            return true
        }
        !xThreeInRow && oThreeInRow -> {
            println("O wins")
            return true
        }
    }

    return false
}

fun countChars(gameState: String, player: Char): Int {
    val lGameState = gameState.toLowerCase()
    val lPlayer = player.toLowerCase()
    var count = 0

    for (i in lGameState.indices) {
        if (lGameState[i] == lPlayer) ++count
    }
    return count
}

fun checkWinCondition(gameState: String, player: Char): Boolean {
    val lGameState = gameState.toLowerCase()
    val lPlayer = player.toLowerCase()

    // check for vertical wins
    return if (lGameState[0] == lPlayer && lGameState[3] == lPlayer && lGameState[6] == lPlayer ||
        lGameState[1] == lPlayer && lGameState[4] == lPlayer && lGameState[7] == lPlayer ||
        lGameState[2] == lPlayer && lGameState[5] == lPlayer && lGameState[8] == lPlayer) true
    // check for horizontal wins
    else if (lGameState[0] == lPlayer && lGameState[1] == lPlayer && lGameState[2] == lPlayer ||
        lGameState[3] == lPlayer && lGameState[4] == lPlayer && lGameState[5] == lPlayer ||
        lGameState[6] == lPlayer && lGameState[7] == lPlayer && lGameState[8] == lPlayer) true
    // check for diagonal wins
    else lGameState[0] == lPlayer && lGameState[4] == lPlayer && lGameState[8] == lPlayer ||
            lGameState[2] == lPlayer && lGameState[4] == lPlayer && lGameState[6] == lPlayer
}