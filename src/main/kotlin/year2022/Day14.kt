package year2022

import Day
import util.rangeTo

class Day14(debug: Boolean = false): Day(2022,14, debug) {
    enum class CoordinateState {
        Air,
        Sand,
        Rock
    }

    val testInput = """
        498,4 -> 498,6 -> 496,6
        503,4 -> 502,4 -> 502,9 -> 494,9
    """.trimIndent()

    private val startMap = (if (debug) testInput else input.asString)
        .split("\n")
        .filterNotEmpty()
        .flatMap { line ->
            line.split(" -> ").map { coord ->
                coord.split(",").map { it.toInt() }.toPair()
            }.windowed(2).map { coord -> coord.toPair() }.flatMap { range -> range.first..range.second }
        }.map { it to CoordinateState.Rock }.toMap()

    fun <T> List<T>.toPair(): Pair<T,T> {
        return if (this.size >= 2) {
            this.first() to this[1]
        } else {
            throw IllegalArgumentException("Size must be >= 2")
        }
    }

    override fun part1(): Any {
        return simulateSandUntilStable(startMap).count { it.value == CoordinateState.Sand }
    }

    private fun simulateSandUntilStable(
        startMap: Map<Pair<Int,Int>, CoordinateState>,
        sandStartAt: Pair<Int,Int> = 500 to 0
    ): Map<Pair<Int,Int>, CoordinateState> {
        println("lowest solid: ${startMap.lowestSolid()}")
        val map = startMap.toMutableMap()
        var sandPos = sandStartAt
        while (sandPos.second <= startMap.lowestSolid()) {
            sandPos = sandStartAt
            while (!isAtRest(sandPos, map)) {
                sandPos = map.nextPos(sandPos)
                println("new sandpos: $sandPos")
            }
            map[sandPos] = CoordinateState.Sand
            println(map)
        }

        return map
    }

    fun Map<Pair<Int,Int>, CoordinateState>.nextPos(sandPos: Pair<Int,Int>): Pair<Int,Int> {
        return when (this.getOrDefault(sandPos.first to sandPos.second + 1, CoordinateState.Air)) {
            CoordinateState.Air -> sandPos.first to sandPos.second + 1
            else -> when (this.getOrDefault(sandPos.first - 1 to sandPos.second + 1, CoordinateState.Air)) {
                CoordinateState.Air -> sandPos.first - 1 to sandPos.second + 1
                else -> when (this.getOrDefault(sandPos.first + 1 to sandPos.second +1, CoordinateState.Air)) {
                    CoordinateState.Air -> sandPos.first + 1 to sandPos.second +1
                    else -> sandPos
                }
            }
        }
    }

    fun isAtRest(sandPos: Pair<Int, Int>, map: Map<Pair<Int,Int>,CoordinateState>): Boolean = map.nextPos(sandPos) == sandPos

    fun Map<Pair<Int,Int>,CoordinateState>.lowestSolid(): Int = this.maxBy { it.key.second }.key.second


}