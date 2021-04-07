package year2017

import Day
import util.minus
import util.plus

class Day19 : Day(2017,19) {
    val testMap = listOf(
        "     |          ",
        "     |  +--+    ",
        "     A  |  C    ",
        " F---|----E|--+ ",
        "     |  |  |  D ",
        "     +B-+  +--+ "
    ).map { it.map { it } }

    val charMap = input.asList.map { it.toList() }

    override fun part1(): Any {
        return charMap.findPath(false).filter { it.isLetter() }
    }

    override fun part2(): Any {
        return charMap.findPath(false).length + 1
    }

    private fun Collection<Collection<Char>>.findPath(lettersOnly: Boolean = true): String {
        val builder = StringBuilder()
        val startingPoint = this.first().indexOfFirst { !it.isWhitespace() } to 0
        var facing = Direction.DOWN
        var last = startingPoint
        var current = last + facing.coordinateDiff
        while (!atCoordinate(current).isWhitespace()) {
            if (atCoordinate(current) == '+') {
                val neighbors = neighborsOf(current).filter { it != last && !atCoordinate(it).isWhitespace()  }
                val diff = neighbors.single() - current
                facing = Direction.fromDiff(diff)
            }
            if (!lettersOnly || atCoordinate(current).isLetter()) {
                builder.append(atCoordinate(current))
            }
            last = current
            current += facing.coordinateDiff
        }
        return builder.toString()
    }

    private fun Collection<Collection<Char>>.atCoordinate(coordinate: Pair<Int,Int>): Char {
        return this.elementAt(coordinate.second).elementAt(coordinate.first)
    }

    private fun Collection<Collection<Char>>.neighborsOf(coordinate: Pair<Int, Int>): List<Pair<Int,Int>> {
        return coordinate.neighbors().filter { it.second in indices && it.first in elementAt(it.second).indices }
    }

    private fun Pair<Int,Int>.neighbors(): List<Pair<Int,Int>> {
        return listOf(
            this.first - 1 to this.second,
            this.first + 1 to this.second,
            this.first to this.second - 1,
            this.first to this.second + 1
        )
    }

    enum class Direction(val coordinateDiff: Pair<Int,Int>) {
        UP(0 to -1),
        DOWN(0 to 1),
        RIGHT(1 to 0),
        LEFT(-1 to 0);

        companion object {
            fun fromDiff(diff: Pair<Int, Int>): Direction {
                return when (diff) {
                    0 to -1 -> UP
                    0 to 1 -> DOWN
                    1 to 0 -> RIGHT
                    -1 to 0 -> LEFT
                    else -> throw IllegalArgumentException()
                }
            }
        }
    }
}
