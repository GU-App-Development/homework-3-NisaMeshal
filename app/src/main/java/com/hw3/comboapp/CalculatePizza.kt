package com.hw3.comboapp

import kotlin.math.ceil

const val SLICES_PER_PIZZA : Double = 8.0

class PizzaCalculator(partySize: Int, var hungerLevel: HungerLevel) {
    var partySize = 0
        set(value) {
            field = if (value >= 0) value else 0
        }

    enum class HungerLevel(var numSlices: Int) {
        LIGHT(2), MEDIUM(3), RAVENOUS(4)
    }

    val totalPizzas: Int
        get() {
            return ceil(partySize * hungerLevel.numSlices / SLICES_PER_PIZZA).toInt()
        }

    init {
        this.partySize = partySize
    }
}