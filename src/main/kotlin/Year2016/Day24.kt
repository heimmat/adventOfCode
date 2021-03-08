package Year2016

import Day
import util.permutate
import kotlin.math.abs

class Day24 : Day(2016,24) {
    private val map = input.asList.filterNotEmpty().flatMapIndexed { y: Int, s: String ->
        s.mapIndexed { x, c ->
            x to y to c
        }
    }.toMap()


    fun Char.isTarget(): Boolean = isDigit()

    private val distanceCalculator = DistanceCalculator(map)

    override fun part1(): Any {
        val start = map.filter { it.value == '0' }.entries.first()
        val targets = (map - start.key).filter { it.value.isTarget() }.toList()
        val targetArrangements: List<List<Pair<Pair<Int,Int>,Char>>> = targets.permutate().map {
            listOf(start.toPair(), *it.toTypedArray())
        }
        //val distanceCalculator = DistanceCalculator(map)
        val minDist = targetArrangements.minOf {
            val plusStart = it.toMutableList().apply { this.add(0, start.toPair()) }
            plusStart.windowed(2).map { distanceCalculator.getDistance(it.first().first, it.last().first) }.sum()
        }
        return minDist
    }

    override fun part2(): Any {
        val start = map.filter { it.value == '0' }.entries.first()
        val targets = (map - start.key).filter { it.value.isTarget() }.toList()
        val targetArrangements: List<List<Pair<Pair<Int,Int>,Char>>> = targets.permutate().map {
            listOf(start.toPair(), *it.toTypedArray(), start.toPair())
        }
        //val distanceCalculator = DistanceCalculator(map)
        val minDist = targetArrangements.minOf {
            val plusStart = it.toMutableList().apply { this.add(0, start.toPair()) }
            plusStart.windowed(2).map { distanceCalculator.getDistance(it.first().first, it.last().first) }.sum()
        }
        return minDist
    }

    private fun Int.factorial(accum: Long = 1): Long {
        return when (this) {
            0,1 -> accum
            else -> (this - 1).factorial(accum * this)
        }
    }



    class DistanceCalculator(val map: Map<Pair<Int,Int>, Char>) {
        private val distanceCache = mutableMapOf<Pair<Pair<Int,Int>, Pair<Int,Int>>, Int>()

        fun getDistance(start: Pair<Int,Int>, end: Pair<Int,Int>): Int {
            return if (end to start in distanceCache.keys) {
                distanceCache[end to start]!!
            } else {
                distanceCache.getOrPut(start to end) {
                    calculateDistance(start, end)
                }
            }

        }


        fun Char.isPassable(): Boolean = this != '#'
        fun Pair<Int,Int>.isPassable() = map[this]?.isPassable() ?: false
        fun Pair<Pair<Int,Int>,Int>.neighbors(): List<Pair<Pair<Int,Int>,Int>> {
            val coord = this.first
            val steps = this.second + 1
            return listOf(
                coord.first + 1 to coord.second to steps,
                coord.first - 1 to coord.second to steps,
                coord.first to coord.second + 1 to steps,
                coord.first to coord.second - 1 to steps
            ).filter { it.first in map.keys }.filter { it.first.isPassable() }
        }

        private fun calculateDistance(start: Pair<Int, Int>, end: Pair<Int, Int>): Int {
            //return abs(start.first - end.first) + abs(start.second - end.second)
            val openList = mutableListOf(
                start to 0
            )
            val closedList = mutableListOf<Pair<Int,Int>>()

            while (true) {
                val filtered = openList.filterNot { it.first in closedList }
                val minSteps = filtered.minOf { it.second }
                val bestGuess = filtered
                    //.filterNot { it.first in closedList }
                    //.filterNot { it.first in closedList }
                    .filter { it.second == minSteps }
                    .minByOrNull { manhattanDistance(it.first, end) }
                //println("Best guess for route between $start and $end is $bestGuess")
                if (bestGuess == null) {
                    println(openList)
                    println(closedList)
                    throw NoSuchElementException("openList is empty")
                } else {
                    openList.remove(bestGuess)
                    closedList.add(bestGuess.first)
                    if (bestGuess.first == end) {
                        return bestGuess.second
                    } else {
                        openList.addAll(bestGuess.neighbors().filterNot { it.first in closedList })
                    }
                }

            }

            return manhattanDistance(start, end)
        }

        private fun manhattanDistance(start: Pair<Int,Int>, end: Pair<Int, Int>): Int {
            return abs(start.first - end.first) + abs(start.second - end.second)
        }
    }

}
