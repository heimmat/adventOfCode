package year2021

import Day
import util.plus
import util.x
import util.y

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

    fun Sequence<Pair<Int,Int>>.takeWhileMovingInX(): Sequence<Pair<Int,Int>> {
        var last: Pair<Int,Int>? = null
        return this.takeWhile {
            val different = last?.x != it.x
            last = it
            different
        }
    }

    override fun part1(): Any {
        return trajectoryFromInitialVelocity(7 to 2).takeWhileRangePossible(xRange = targetXRange, yRange = targetYRange).takeWhileMovingInX().toList()
    }
}