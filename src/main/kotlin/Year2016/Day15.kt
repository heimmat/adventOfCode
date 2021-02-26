package Year2016

import Day

class Day15 : Day(2016,15) {

    val testDiscs = listOf(
        Disc(5,4),
        Disc(2,1)
    )

    val discs = input.asList.filterNotEmpty().map {
        val split = it.split(" ")
        val numPositions = split[3].toInt()
        val initialPosition = split.last().dropLast(1).toInt()
        Disc(numPositions, initialPosition)
    }

    override fun part1(): Any {
        return findFirstDropPoint(discs)
    }

    override fun part2(): Any {
        return findFirstDropPoint(discs + Disc(11,0))
    }

    fun findFirstDropPoint(discs: List<Disc>): Int {
        var time = 0
        while (!discs.mapIndexed { index, disc -> disc.positionAtTime(time + 1 + index) == 0 }.all { it }) {
            time++
        }
        return time
    }

    data class Disc(val numPositions: Int, val initialPosition: Int) {
        fun positionAtTime(time: Int): Int {
            return (initialPosition + time ) % numPositions
        }
    }

}
