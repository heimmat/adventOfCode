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
        val virusCarrier = BasicCarrier(input.asList.filterNotEmpty())
        repeat(10_000) {
            virusCarrier.burst()
        }
        return virusCarrier.infected
    }

    override fun part2(): Any {
        val carrier = EvolvedCarrier(input.asList.filterNotEmpty())
        repeat(10_000_000) {
            carrier.burst()
        }
        return carrier.infected
    }

    interface VirusCarrier {
        fun burst() {}
        val infected: Int
    }

    class BasicCarrier(initialMap: List<String>): VirusCarrier {
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

        override var infected = 0; private set

        val Pair<Int,Int>.isInfected: Boolean get() = map.getOrDefault(this, false)

        override fun burst() {
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

    class EvolvedCarrier(initialMap: List<String>): VirusCarrier {
        val map: MutableMap<Pair<Int,Int>, InfectionState> = initialMap.flatMapIndexed { yIndex: Int, s: String ->
            val y = yIndex - initialMap.size/2
            s.mapIndexed { xIndex, c ->
                val x = xIndex - initialMap.size/2
                (x to y) to if (c == '#') InfectionState.INFECTED else InfectionState.CLEAN
            }
        }.toMap().toMutableMap()

        var direction = Direction.UP; private set
        var position = 0 to 0; private set

        override var infected = 0; private set

        val Pair<Int,Int>.isInfected: Boolean get() = map.getOrDefault(this, InfectionState.CLEAN) == InfectionState.INFECTED
        val Pair<Int,Int>.infectionState: InfectionState get() = map.getOrDefault(this, InfectionState.CLEAN)

        override fun burst() {
            val offset = when (position.infectionState) {
                InfectionState.CLEAN -> -1
                InfectionState.WEAKENED -> 0
                InfectionState.INFECTED -> 1
                InfectionState.FLAGGED -> 2
            }
            val directionValues = Direction.values()
            direction = directionValues[(direction.ordinal + directionValues.size + offset) % directionValues.size]
            val newState = InfectionState.values()[(position.infectionState.ordinal + 1) % InfectionState.values().size]
            if (newState == InfectionState.INFECTED) {
                infected++
            }
            map[position] = newState
            position += direction.coordinateDiff
        }

        enum class InfectionState {
            CLEAN,
            WEAKENED,
            INFECTED,
            FLAGGED
        }
    }
}
