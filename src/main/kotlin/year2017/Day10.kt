package year2017

import Day
import util.CircularList

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
        //val suffix = listOf(17, 31, 73, 47, 23)
       // val inputLengths = inputLengths.map { it.toInt() } + suffix
        return KnotHasher().hashInts(inputLengths.map { it.toInt() })
    }
}
