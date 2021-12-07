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

    private fun fishCountAtTime(initialFishes: List<Int>, time: Int): Long {
        //With a little help from Todd Ginsberg https://todd.ginsberg.com/post/advent-of-code/2021/day6/
        fun List<Long>.reproduce(): List<Long> {
            val reproducing = this.first()
            val rest = this.drop(1).toMutableList()
            rest[6] += reproducing
            rest.add(reproducing)
            return rest
        }

        val timeBasedRepresentation = initialFishes.groupBy { it }.map { it.key to it.value.size }.toMap()
        val fishesInState = List<Long>(9) { index ->
            timeBasedRepresentation[index]?.toLong() ?: 0L
        }
        var state = fishesInState
        repeat(time) {
            state = state.reproduce()
        }
        return state.sum()




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

    override fun part2(): Any {
        return fishCountAtTime(initialState, 256)
    }

}