package util

import kotlin.math.abs

val Pair<Int,Int>.x : Int get() = first
val Pair<Int,Int>.y : Int get() = second
infix fun Pair<Int,Int>.manhattanTo(that: Pair<Int,Int>): Int {
    return abs(this.first - that.first) + abs(this.second - that.second)
}

operator fun Pair<Int,Int>.plus(that: Pair<Int, Int>): Pair<Int,Int> {
    return this.first + that.first to this.second + that.second
}

operator fun Pair<Int,Int>.minus(that: Pair<Int, Int>): Pair<Int,Int> {
    return this.first - that.first to this.second - that.second
}

infix fun Pair<Int,Int>.manhattanDistanceTo(that: Pair<Int, Int>): Int {
    return abs(this.first - that.first) + abs(this.second - that.second)
}