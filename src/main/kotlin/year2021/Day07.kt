package year2021

import Day

class Day07(debug: Boolean = false): Day(2021,7, debug) {
    private val testInput = "16,1,2,0,4,2,7,1,2,14"

    val crabPositions = (if (debug) testInput else input.asString.trim())
        .split(",")
        .map { it.toInt() }

    override fun part1(): Any {
        val maxPos = crabPositions.maxOf { it }
        val minPos = crabPositions.minOf { it }

        return (minPos..maxPos).minOf { pos ->
            crabPositions.sumBy { crab ->
                Math.abs(crab - pos)
            }
        }
    }

    override fun part2(): Any {
        val maxPos = crabPositions.maxOf { it }
        val minPos = crabPositions.minOf { it }

        return (minPos..maxPos).minOf { pos ->
            crabPositions.sumBy { crab ->
                val dist = Math.abs(crab - pos)
                movementCost(dist)
            }
        }
    }

    private fun movementCost(distance: Int): Int {
        if (distance == 0) {
            return distance
        } else {
            return distance + movementCost(distance - 1)
        }
    }
}