package year2021

import Day

class Day06(debug: Boolean = false): Day(2021,6, debug) {
    private val testInput = "3,4,3,1,2"

    val initialState = (if (debug) testInput else input.asString.filterNotWhitespace())
        .split(",")
        .map { it.toInt() }

    fun List<Int>.tick(): List<Int> {
        val newFishesCount = count { it == 0 }
        return this.map { it - 1 }
            .map { if (it == -1) 6 else it } + List(newFishesCount) { 8 }

    }

    override fun part1(): Any {
        var fishes = initialState
        repeat(80) {
            fishes = fishes.tick()
            if (debug && it < 18) {
                println(fishes)
            }
        }
        return fishes.size
    }

}