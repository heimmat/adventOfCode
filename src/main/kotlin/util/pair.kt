package util

import kotlin.math.abs

val Pair<Int,Int>.x : Int get() = first
val Pair<Int,Int>.y : Int get() = second
infix fun Pair<Int,Int>.manhattanTo(that: Pair<Int,Int>): Int {
    return abs(this.first - that.first) + abs(this.second - that.second)
}