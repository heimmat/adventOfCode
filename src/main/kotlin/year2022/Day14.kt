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

    override fun part2(): Any {
        return simulateSandWithFloor(startMap).count { it.value == CoordinateState.Sand }
    }

    fun Map<Pair<Int,Int>, CoordinateState>.print(mapHasFloor: Boolean = false): String {
        val sb = StringBuilder()
        val minX = this.keys.minOf { it.first }
        val minY = this.keys.minOf { it.second }
        val maxX = this.keys.maxOf { it.first }
        val maxY = this.keys.maxOf { it.second }
        for (y in minY..maxY) {
            for (x in minX..maxX) {
                val char = when (getOrDefaultWithFloor(x to y, CoordinateState.Air, mapHasFloor)) {
                    CoordinateState.Air -> '.'
                    CoordinateState.Sand -> 'o'
                    CoordinateState.Rock -> '#'
                }
                sb.append(char)
            }
            sb.appendLine()
        }
        return sb.toString()
    }



    private fun simulateSandUntilStable(
        startMap: Map<Pair<Int,Int>, CoordinateState>,
        sandStartAt: Pair<Int,Int> = 500 to 0,
    ): Map<Pair<Int,Int>, CoordinateState> {
        if (debug) println("lowest solid: ${startMap.lowestSolid()}")
        val map = startMap.toMutableMap()
        var sandPos = sandStartAt
        while (sandPos.second <= startMap.lowestSolid()) {
            sandPos = sandStartAt
            while (!isAtRest(sandPos, map)) {
                sandPos = map.nextPos(sandPos)
                if (debug) println("new sandpos: $sandPos")
                if (sandPos.second > startMap.lowestSolid()) break
            }
            if (sandPos.second <= startMap.lowestSolid()) {
                map[sandPos] = CoordinateState.Sand
            }
            if (debug) println(map)
        }

        return map
    }

    private fun simulateSandWithFloor(
        startMap: Map<Pair<Int, Int>, CoordinateState>,
        sandStartAt: Pair<Int, Int> = 500 to 0
    ):Map<Pair<Int,Int>, CoordinateState> {
        val map = startMap.toMutableMap()
        var sandPos = sandStartAt
        while (map.nextPos(sandPos, true) != sandStartAt) {
            sandPos = sandStartAt
            while (!isAtRest(sandPos, map, true)) {
                sandPos = map.nextPos(sandPos, true)
                if (debug) println("new sandpos: $sandPos")
            }
            map[sandPos] = CoordinateState.Sand
        }
        return map
    }

    fun Map<Pair<Int,Int>, CoordinateState>.nextPos(sandPos: Pair<Int,Int>, mapHasFloor: Boolean = false): Pair<Int,Int> {
        return when (this.getOrDefaultWithFloor(sandPos.first to sandPos.second + 1, CoordinateState.Air, mapHasFloor)) {
            CoordinateState.Air -> sandPos.first to sandPos.second + 1
            else -> when (this.getOrDefaultWithFloor(sandPos.first - 1 to sandPos.second + 1, CoordinateState.Air, mapHasFloor)) {
                CoordinateState.Air -> sandPos.first - 1 to sandPos.second + 1
                else -> when (this.getOrDefaultWithFloor(sandPos.first + 1 to sandPos.second +1, CoordinateState.Air, mapHasFloor)) {
                    CoordinateState.Air -> sandPos.first + 1 to sandPos.second +1
                    else -> sandPos
                }
            }
        }
    }

    fun Map<Pair<Int,Int>, CoordinateState>.getOrDefaultWithFloor(key: Pair<Int,Int>, defaultValue: CoordinateState, mapHasFloor: Boolean, floorLevel: Int = lowestSolid() + 2): CoordinateState {
        return if (!mapHasFloor || key.second != floorLevel) {
            this.getOrDefault(key, defaultValue)
        }  else {
            CoordinateState.Rock
        }
    }

    fun isAtRest(sandPos: Pair<Int, Int>, map: Map<Pair<Int,Int>,CoordinateState>, mapHasFloor: Boolean = false): Boolean = map.nextPos(sandPos, mapHasFloor) == sandPos

    fun Map<Pair<Int,Int>,CoordinateState>.lowestSolid(): Int = this.maxBy { it.key.second }.key.second


}