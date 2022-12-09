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