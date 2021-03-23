package year2017

import Day
import util.CircularList
import util.toCircularList

class Day10 : Day(2017,10) {
    private val inputLengths = input.asString.trim()

    override fun part1(): Any {
        val inputLengths = inputLengths.split(",").map { it.toInt() }
        var circularList = CircularList(0..255)//CircularList(listOf(0,1,2,3,4))
        var skipSize = 0
        var currentPosition = 0
        inputLengths.forEach {
            circularList = circularList.pinchAndTwist(currentPosition, it)
            currentPosition += it + skipSize
            skipSize++
        }
        return circularList[0] * circularList[1]
    }

    override fun part2(): Any {
        val suffix = listOf(17, 31, 73, 47, 23)
        val inputLengths = inputLengths.map { it.toInt() } + suffix
        var circularList = CircularList(0..255)//CircularList(listOf(0,1,2,3,4))
        var skipSize = 0
        var currentPosition = 0
        repeat(64) {
            inputLengths.forEach {
                circularList = circularList.pinchAndTwist(currentPosition, it)
                currentPosition += it + skipSize
                skipSize++
            }
        }
        val denseHash = circularList.denseHash()
        return denseHash.knotHash()

    }

    fun List<Int>.knotHash(): String {
        return map {
            it.toString(16).padStart(2, '0')
        }.joinToString("")
    }

    fun CircularList<Int>.denseHash(): List<Int> = windowed(16,16).map {
        it.reduce { acc, i ->
            acc xor i
        }
    }

    fun CircularList<Int>.pinchAndTwist(currentPosition: Int, length: Int): CircularList<Int> {
        val range = currentPosition until currentPosition + length
        val reversedRange: List<Int> = range.toList().reversed()
        val reversedOrder = range
            .mapIndexed { index, it ->
                it % size to reversedRange[index]%size
            }.toMap()
        return mapIndexed { index, i ->
            if (index in reversedOrder.keys) {
                this[reversedOrder[index]!!]
            } else {
                i
            }
        }.toCircularList()
    }




}
