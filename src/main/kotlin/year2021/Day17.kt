package year2021

import Day
import util.plus
import util.x
import util.y
import kotlin.math.absoluteValue

class Day17(debug: Boolean = false): Day(2021,17, debug) {

    val inputWrapped = if (debug) "target area: x=20..30, y=-10..-5" else input.asString.trim()

    val targetXRange = inputWrapped.substringAfter("x=").substringBefore(",").split("..").map { it.toInt() }.toRange()
    val targetYRange = inputWrapped.substringAfter("y=").split("..").map { it.toInt() }.toRange()

    fun List<Int>.toRange() = this.first()..this.last()

    fun trajectoryFromInitialVelocity(velocity: Pair<Int,Int>): Sequence<Pair<Int,Int>> {
        val initialPosition = 0 to 0
        return velocitiesFromInitial(velocity).runningFold(initialPosition) { acc, pair ->
            acc + pair
        }
    }

    fun Pair<Int,Int>.tick(): Pair<Int,Int> {
        return if (x > 0) {
            x - 1 to y - 1
        } else if (x == 0) {
            x to y - 1
        } else if (x < 0) {
            x + 1 to y -1
        }  else {
            error("Inaccessible state")
        }
    }

    fun velocitiesFromInitial(initial: Pair<Int,Int>): Sequence<Pair<Int,Int>> = generateSequence(initial) {
        it.tick()
    }

    fun Sequence<Pair<Int,Int>>.takeWhileRangePossible(xRange: IntRange, yRange: IntRange): Sequence<Pair<Int,Int>> {
        return this.takeWhile {
            it.y >= yRange.first && it.x <= xRange.last
        }
    }

    fun Sequence<Pair<Int,Int>>.takeWhileMovingInXOrInRange(xRange: IntRange, yRange: IntRange): Sequence<Pair<Int,Int>> {
        var last: Pair<Int,Int>? = null
        return this.takeWhile {
            val different = last?.x != it.x
            last = it
            different || it.x in xRange
        }
    }

    fun Sequence<Pair<Int,Int>>.takeWhileMovingAndPossible(xRange: IntRange, yRange: IntRange): List<Pair<Int,Int>> {
        return this.takeWhileRangePossible(xRange, yRange)
            .takeWhileMovingInXOrInRange(xRange, yRange)
            .toList()
    }

    val possibleInitialVelocities = run {
        val sequenceOfInitialVelocities = sequence {
            for (x in 1..this@Day17.targetXRange.last) {
                for (y in this@Day17.targetYRange.first..this@Day17.targetYRange.first.absoluteValue) {
                    this.yield(x to y)
                }
            }
        }
        val range = sequence {
            for (x in this@Day17.targetXRange) {
                for (y in this@Day17.targetYRange) {
                    this.yield(x to y)
                }
            }
        }
        sequenceOfInitialVelocities
            .map {
                trajectoryFromInitialVelocity(it)
                    .takeWhileMovingAndPossible(xRange = targetXRange, yRange = targetYRange)
            }
            .filter { it.isNotEmpty() }
            .filter { it.any { it in range } }
    }

    override fun part1(): Any {
        return possibleInitialVelocities.maxOf {
            it.maxOf{ it.y }
        }

    }

    override fun part2(): Any {
        return possibleInitialVelocities.toList().size
    }

}