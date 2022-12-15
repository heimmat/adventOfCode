package util

import kotlin.math.abs
import kotlin.math.sign

val Pair<Int,Int>.x : Int get() = first
val Pair<Int,Int>.y : Int get() = second

operator fun Pair<Int,Int>.plus(that: Pair<Int, Int>): Pair<Int,Int> {
    return this.first + that.first to this.second + that.second
}

operator fun Pair<Int,Int>.minus(that: Pair<Int, Int>): Pair<Int,Int> {
    return this.first - that.first to this.second - that.second
}

infix fun Pair<Int,Int>.manhattanDistanceTo(that: Pair<Int, Int>): Int {
    return abs(this.first - that.first) + abs(this.second - that.second)
}

fun Pair<Int,Int>.isTouching(that: Pair<Int,Int>): Boolean {
    return this.first == that.first && abs(this.second - that.second) <= 1
            || this.second == that.second && abs(this.first - that.first) <= 1
            || abs(this.first - that.first) == 1 && abs(this.second - that.second) == 1
}

fun Pair<Int,Int>.moveTowards(that: Pair<Int, Int>): Pair<Int,Int> {
    val diff = that - this
    val xStep = if (abs(diff.first) > 1) diff.first.sign else diff.first
    val yStep = if (abs(diff.second) > 1) diff.second.sign else diff.second
    return this + (xStep to yStep)
}

operator fun Pair<Int,Int>.rangeTo(that: Pair<Int, Int>): List<Pair<Int,Int>> {
    if (this.first == that.first || this.second == that.second) {
        if (this.first == that.first) {
            return (this.second..that.second).map {
                this.first to it
            }
        } else {
            return (this.first..that.first).map {
                it to this.second
            }
        }
    } else {
        throw IllegalArgumentException("X or Y coordinate must equal")
    }
}

fun Pair<Int,Int>.coordinatesInManhattanDistance(distance: Int): Set<Pair<Int,Int>> {
    val mutableSet = mutableSetOf<Pair<Int,Int>>()
    for (xDiff in 0..distance) {
        for (yDiff in 0..distance-xDiff) {
            mutableSet.addAll(listOf(
                (x + xDiff) to (y + yDiff),
                (x + xDiff) to (y - yDiff),
                (x - xDiff) to (y + yDiff),
                (x - xDiff) to (y - yDiff)
            ))
        }
    }
    return mutableSet
}