package year2017 

import Day

class Day01: Day(2017,1) {

    private val cleanedInput = input.asString.trim()

    @ExperimentalStdlibApi
    override fun part1(): Any {
        return cleanedInput.checksumByNext()
    }

    @ExperimentalStdlibApi
    override fun part2(): Any {
        return cleanedInput.checksumByHalfway()
    }

    @ExperimentalStdlibApi
    private fun String.checksumByNext(): Int {
        return mapIndexed { index, c ->
            if (c == this[nextAfter(index)]) c.digitToIntOrNull()!! else 0
        }.sum()
    }

    @ExperimentalStdlibApi
    private fun String.checksumByHalfway(): Int {
        return mapIndexed { index, c ->
            if (c == this[halfAround(index)]) c.digitToIntOrNull()!! else 0
        }.sum()
    }

    private fun String.halfAround(index: Int): Int {
        if (index in indices) {
            return (index + length/2) % length
        } else {
            throw IllegalArgumentException("Index $index out of range $indices")
        }
    }

    private fun String.nextAfter(index: Int): Int {
        if (index in indices) {
            return (index + 1) % length
        } else {
            throw IllegalArgumentException("Index $index out of range $indices")
        }
    }
}