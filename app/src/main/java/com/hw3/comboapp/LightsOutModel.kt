package com.hw3.comboapp

import kotlin.random.Random

const val GRID_SIZE = 3

class LightsOutModel {
    private val lightsGrid = Array(GRID_SIZE) { Array(GRID_SIZE) {true} }

    //to set random lights on and off
    fun newGame() {
        for (row in 0 until GRID_SIZE) {
            for (col in 0 until GRID_SIZE) {
                lightsGrid[row][col] = Random.nextBoolean()
            }
        }
    }

    //returns true/false based on light condition on a certain row,col of grid
    fun isLightOn(row: Int, col: Int) : Boolean{
        return lightsGrid[row][col]
    }

    //to switch the light. All the if statements are like that due to 0 based indexing
    fun selectLight(row: Int, col: Int) {
        lightsGrid[row][col] = !lightsGrid[row][col]
        if (row > 0) {
            lightsGrid[row - 1][col] = !lightsGrid[row - 1][col]
        }
        if (row < GRID_SIZE - 1) {
            lightsGrid[row + 1][col] = !lightsGrid[row + 1][col]
        }
        if (col > 0) {
            lightsGrid[row][col - 1] = !lightsGrid[row][col - 1]
        }
        if (col < GRID_SIZE - 1) {
            lightsGrid[row][col + 1] = !lightsGrid[row][col + 1]
        }
    }

    //checks if all the lights in the grid are off if not, returns false.
    val isGameOver: Boolean
        get() {
            for(row in 0 until GRID_SIZE) {
                for(col in 0 until GRID_SIZE) {
                    if (lightsGrid[row][col]) {
                        return false
                    }
                }
            }
            return true
        }

    var state: String
        get() {
            val boardString = StringBuilder()
            for (row in 0 until GRID_SIZE) {
                for (col in 0 until GRID_SIZE) {
                    val value = if (lightsGrid[row][col]) 'T' else 'F'
                    boardString.append(value)
                }
            }
            return boardString.toString()
        }
        set(value) {
            var index = 0
            for (row in 0 until GRID_SIZE) {
                for (col in 0 until GRID_SIZE) {
                    lightsGrid[row][col] = value[index] == 'T'
                    index++
                }
            }
        }
}