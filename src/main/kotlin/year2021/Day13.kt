package year2021

import Day
import util.toRange

class Day13(debug: Boolean = false): Day(2021, 13, debug) {
    private val testInput = """
        6,10
        0,14
        9,10
        0,3
        10,4
        4,11
        6,0
        6,12
        4,1
        0,13
        10,12
        3,4
        3,0
        8,4
        1,10
        2,14
        8,10
        9,0

        fold along y=7
        fold along x=5
    """.trimIndent()

    private val inputSplit = (if (debug) testInput else  input.asString.trim())
        .split("\n\n")

    val points = inputSplit.first().split("\n").map {
        val split = it.split(",").map { it.toInt() }
        split.first() to split.last()
    }
    val instructions = inputSplit.last().split("\n")


    fun Collection<Pair<Int,Int>>.foldX(xToFold: Int): Collection<Pair<Int,Int>> {
        return this.map {
            Math.abs(xToFold - it.first) to it.second
        }.toSet()
    }

    fun Collection<Pair<Int,Int>>.foldY(yToFold: Int): Collection<Pair<Int,Int>> {
        return this.map {
            it.first to Math.abs(yToFold - it.second)
        }.toSet()
    }

    fun Collection<Pair<Int,Int>>.foldPaper(instruction: String): Collection<Pair<Int,Int>> {
        val relevantPart = instruction.split(" ").last().split("=")
        if (relevantPart.first() == "x") {
            return this.foldX(relevantPart.last().toInt())
        } else if (relevantPart.first() == "y") {
            return this.foldY(relevantPart.last().toInt())
        } else {
            throw IllegalArgumentException("Expected \"x\" or \"y\", got \"${relevantPart.first()}\". Instruction was \"$instruction\"")
        }
    }

    fun Collection<Pair<Int,Int>>.print(): String {
        val builder = StringBuilder()
        val xRange = 0..maxOf { it.first }
        val yRange = 0..maxOf { it.second }
        for (y in yRange) {
            for (x in xRange) {
                if (this.contains(x to y)) {
                    builder.append("#")
                } else {
                    builder.append(".")
                }
            }
            builder.append("\n")
        }
        return builder.toString()
    }

    override fun part1(): Any {
        return points.foldPaper(instructions.first()).size
    }

    override fun part2(): Any {
        var points: Collection<Pair<Int,Int>> = points
        instructions.forEach {
            points = points.foldPaper(it)
        }



        return "\n${points.print()}"

    }

}