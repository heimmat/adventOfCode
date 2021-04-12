package year2017

import Day
import util.plus

class Day22 : Day(2017,22) {

    val testMap = listOf(
        "..#",
        "#..",
        "..."
    )

    override fun part1(): Any {
        val virusCarrier = VirusCarrier(input.asList.filterNotEmpty())
        repeat(10_000) {
            virusCarrier.burst()
        }
        return virusCarrier.infected
    }

    class VirusCarrier(initialMap: List<String>) {
        init {
            if (initialMap.any { it.length != initialMap.size }) throw IllegalArgumentException()
        }
        val map: MutableMap<Pair<Int,Int>, Boolean> = initialMap.flatMapIndexed { yIndex: Int, s: String ->
            val y = yIndex - initialMap.size/2
            s.mapIndexed { xIndex, c ->
                val x = xIndex - initialMap.size/2
                (x to y) to (c == '#')
            }
        }.toMap().toMutableMap()

        var direction = Direction.UP; private set
        var position = 0 to 0; private set

        var infected = 0; private set

        val Pair<Int,Int>.isInfected: Boolean get() = map.getOrDefault(this, false)

        fun burst() {
            val offset = if (position.isInfected) 1 else -1
            val directionValues = Direction.values()
            //println("Old direction is $direction")
            //println("Position $position is infected: ${position.isInfected}")
            direction = directionValues[(direction.ordinal + directionValues.size + offset) % directionValues.size]
            //println("New direction is $direction")
            val newInfection = !position.isInfected
            map[position] = newInfection
            if (newInfection) {
                infected++
            }
            position += direction.coordinateDiff
            //println("New position is $position")
        }
    }
}
