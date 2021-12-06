package year2021

import Day
import util.minus
import util.plus

class Day05(debug: Boolean = false): Day(2021, 5, debug) {
    private val testInput = """
        0,9 -> 5,9
        8,0 -> 0,8
        9,4 -> 3,4
        2,2 -> 2,1
        7,0 -> 7,4
        6,4 -> 2,0
        0,9 -> 2,9
        3,4 -> 1,4
        0,0 -> 8,8
        5,5 -> 8,2
    """.trimIndent().split("\n")

    val linesOfVents = (if (debug) testInput else input.asList)
        .map {
            val (start,end) = it.split(" -> ").map {
                val (x,y) = it.split(",").map { it.toInt() }
                x to y
            }
            (start to end)
        }.also { if (debug) println(it) }

    private fun Pair<Pair<Int,Int>, Pair<Int,Int>>.allBetween(): List<Pair<Int,Int>> {
        return if (first.first == second.first) { // Column
            val range = if (first.second < second.second) first.second..second.second else first.second downTo second.second
            range.map {
                first.first to it
            }
        } else if (first.second == second.second) {
            val range = if (first.first < second.first) first.first..second.first else first.first downTo second.first
            range.map {
                it to first.second
            }
        } else {
            //Determine direction
            val delta = second - first
            val deltaX = if (delta.first > 0) 1 else -1
            val deltaY = if (delta.second > 0) 1 else -1
            val list = mutableListOf(first, second)
            var inbetween = first + (deltaX to deltaY)
            while (inbetween != second) {
                list.add(inbetween)
                inbetween += (deltaX to deltaY)
            }
            list
        }
    }

    override fun part1(): Any {
        return linesOfVents
            .filter { it.first.first == it.second.first || it.first.second == it.second.second }
            .also { if (debug) println(it) }
            .flatMap { it.allBetween() }
            .groupBy { it }
            .map { it.key to it.value.size }
            .filter { it.second >= 2 }
            .count()
    }

    override fun part2(): Any {
        return linesOfVents
            .flatMap { it.allBetween() }
            .groupBy { it }
            .map { it.key to it.value.size }
            .filter { it.second >= 2 }
            .count()
    }
}