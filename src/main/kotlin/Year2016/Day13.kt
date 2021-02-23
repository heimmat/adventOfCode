package Year2016

import Day
import kotlin.math.abs

class Day13 : Day(2016,13) {
    private val favoriteNumber = input.asString.trim().toInt()

    override fun part1(): Any {
        return aStar(1 to 1, 31 to 39)?.stepsFromOrigin ?: "null"
    }

    override fun part2(): Any {
        val setOfPosition = mutableSetOf<Position>(Position(1 to 1, 0))
        repeat(50) { iteration ->
            val currentPositions = setOfPosition.filter { it.stepsFromOrigin == iteration }
            setOfPosition.addAll(currentPositions.flatMap { it.possibleNeighbors })
        }
        return setOfPosition.size
    }

    private fun aStar(fromCoordinates: Pair<Int,Int>, toCoordinates: Pair<Int,Int>, debug: Boolean = false): Position? {
        val initialPosition = Position(fromCoordinates, 0)
        val openList = mutableListOf<Position>(initialPosition)
        val closedList = mutableListOf<Position>()
        var found = false

        try {
            while (!found) {
                if (debug) println()
                val bestGuess = openList.sortedBy { it.stepsFromOrigin }.sortedBy { it.manhattanDistanceFrom(toCoordinates) }.first()
                if (debug) println(openList)
                if (debug) println("bestGuess: $bestGuess")
                openList.remove(bestGuess)
                closedList.add(bestGuess)
                if (bestGuess.coordinates == toCoordinates) {
                    if (debug) println("Found a solution")
                    found = true
                    if (debug) println("$bestGuess is the solution")
                    return bestGuess
                } else {
                    bestGuess.possibleNeighbors.forEach { neighbor ->
                        if (neighbor !in closedList) {
                            if (neighbor in openList) {
                                if (debug) println("Already have this neighbor in open list")
                                val alreadyInOpenList = openList.find { ole ->
                                    neighbor == ole
                                }!!
                                if (alreadyInOpenList.stepsFromOrigin >= neighbor.stepsFromOrigin) {
                                    if (debug) println("Found a shorter path to ${neighbor.coordinates}")
                                    openList.remove(alreadyInOpenList)
                                    openList.add(neighbor)
                                }
                            } else {
                                openList.add(neighbor)
                            }

                        }
                    }
                }
            }
        } catch (e: NoSuchElementException) {
            println("Open list is empty. No valid solution found")

        }
        return null
    }


    private fun manhattanDistance(from: Pair<Int, Int>, to: Pair<Int, Int>) = abs(from.first - to.first) + abs(from.second - to.second)

    private fun drawSpace(xRange: IntRange, yRange: IntRange): String {
        val builder = StringBuilder()
        for (y in yRange) {
            for (x in xRange) {
                builder.append(if (openSpace(x,y)) '.' else '#')
            }
            builder.append('\n')
        }
        return builder.toString()
    }

    @ExperimentalUnsignedTypes
    private fun openSpace(x: UInt, y: UInt): Boolean {
        val intermediateSum = x*x + 3u*x + 2u*x*y + y + y*y
        val sum = intermediateSum + favoriteNumber.toUInt()
        return sum.countOneBits() % 2 == 0
    }

    private fun openSpace(x: Int, y: Int): Boolean {
        if (x >= 0 && y >= 0) {
            val intermediateSum = x*x + 3*x + 2*x*y + y + y*y
            val sum = intermediateSum + favoriteNumber
            return sum.countOneBits() % 2 == 0
        } else {
            throw IllegalArgumentException("x and y need to be >= 0")
        }
    }

    private fun Pair<Int,Int>.openSpace(): Boolean {
        return openSpace(first, second)
    }

    fun Pair<Int,Int>.neighbors(): List<Pair<Int,Int>> {
        return listOf(
            first - 1 to second,
            first + 1 to second,
            first to second - 1,
            first to second + 1
        ).filter { it.first >= 0 && it.second >= 0 }
    }

    inner class Position(val coordinates: Pair<Int,Int>, val stepsFromOrigin: Int) {
        val openSpace: Boolean by lazy { coordinates.openSpace() }
        val possibleNeighbors: List<Position> by lazy { coordinates.neighbors().filter { it.openSpace() }.map { Position(it, stepsFromOrigin + 1) }}

        fun manhattanDistanceFrom(coordinates: Pair<Int, Int>): Int = manhattanDistance(this.coordinates, coordinates)

        override fun toString(): String {
            return "(${coordinates.first},${coordinates.second}): ${if (openSpace) '.' else '#'} - $stepsFromOrigin"
        }

        override fun equals(other: Any?): Boolean {
            if (other is Position) {
                return other.coordinates == coordinates
            } else {
                return false
            }

        }

        override fun hashCode(): Int {
            return coordinates.hashCode()
        }


    }



}
