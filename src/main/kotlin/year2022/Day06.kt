package year2022

import Day
import util.indexOfStartOfNDistinctChars

class Day06(debug: Boolean = false): Day(2022,6,debug) {
    override fun part1(): Any {
        return if (debug) {
            "mjqjpqmgbljsphdztnvjfqwrcgsmlb".indexOfPacketStart()
        } else {
            input.asString.indexOfPacketStart()
        }
    }

    override fun part2(): Any {
        return input.asString.indexOfMessageStart()
    }

    fun String.indexOfPacketStart(): Int {
        return this.indexOfStartOfNDistinctChars(4) + 4
    }

    fun String.indexOfMessageStart(): Int {
        return this.indexOfStartOfNDistinctChars(14) + 14
    }
}