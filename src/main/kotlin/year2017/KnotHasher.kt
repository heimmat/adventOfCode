package year2017

import util.CircularList
import util.toCircularList

class KnotHasher(private val repetitions: Int = 64, private val listSize: Int = 256) {

    fun hashInts(ints: List<Int>): String {
        var circularList = CircularList(0 until listSize)
        var skipSize = 0
        var currentPosition = 0
        repeat(repetitions) {
            ints.forEach {
                circularList = circularList.pinchAndTwist(currentPosition, it)
                currentPosition += it + skipSize
                skipSize++
            }
        }
        val denseHash = circularList.denseHash()
        return denseHash.knotHash()
    }
    fun hashString(string: String): String {
        return hashInts(string.map { it.toInt() })
    }

    private fun List<Int>.knotHash(): String {
        return map {
            it.toString(16).padStart(2, '0')
        }.joinToString("")
    }

    private fun CircularList<Int>.denseHash(): List<Int> = windowed(16,16).map {
        it.reduce { acc, i ->
            acc xor i
        }
    }
}