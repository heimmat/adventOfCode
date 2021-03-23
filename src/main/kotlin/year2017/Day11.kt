package year2017

import Day
import util.manhattanDistanceTo
import util.minus
import util.plus
import kotlin.math.abs

class Day11 : Day(2017,11) {
    val path = input.asString.trim().split(",").map {
        HexagonalTileSide.valueOf(it.toUpperCase())
    }

    override fun part1(): Any {
        val endOfPath = path.fold(0 to 0) { acc, hexagonalTileSide ->
            acc + hexagonalTileSide.coordinateDiff
        }
        return endOfPath.stepsFromOrigin()
    }

    override fun part2(): Any {
        val intermediateSteps = path.runningFold(0 to 0) { acc, hexagonalTileSide ->
            acc + hexagonalTileSide.coordinateDiff
        }
        return intermediateSteps.maxByOrNull { it manhattanDistanceTo(0 to 0) }?.stepsFromOrigin()!!
    }

    private fun Pair<Int,Int>.stepsFromOrigin(): Int {
        val divisor = this / HexagonalTileSide.NE.coordinateDiff
        val rem = this % HexagonalTileSide.NE.coordinateDiff
        return divisor + rem / HexagonalTileSide.N.coordinateDiff
    }



    enum class HexagonalTileSide(val coordinateDiff: Pair<Int,Int>) {
        NW(-1 to 1),
        N(0 to 2),
        NE(1 to 1),
        SE(1 to -1),
        S(0 to -2),
        SW(-1 to -1)
    }

    operator fun Pair<Int,Int>.rem(that: Pair<Int, Int>): Pair<Int,Int> {
        val divided = this / that
        return this - that * divided
    }

    operator fun Pair<Int,Int>.div(that: Pair<Int,Int>): Int {
        var multiplicator = 0
        var product: Pair<Int,Int> = that * multiplicator
        while (abs(product.first) <= abs(this.first) &&  abs(product.second) <= abs(this.second)) {
            multiplicator++
            product = that * multiplicator
        }

        return multiplicator - 1
    }
    
    operator fun Pair<Int,Int>.times(that: Int): Pair<Int,Int> {
        return this.first * that to this.second * that
    }


}
