package year2022

import Day
import util.coordinatesInManhattanDistance
import util.manhattanDistanceTo
import util.x
import util.y

class Day15(debug: Boolean = false): Day(2022,15, debug) {
    private val testInput = """
        Sensor at x=2, y=18: closest beacon is at x=-2, y=15
        Sensor at x=9, y=16: closest beacon is at x=10, y=16
        Sensor at x=13, y=2: closest beacon is at x=15, y=3
        Sensor at x=12, y=14: closest beacon is at x=10, y=16
        Sensor at x=10, y=20: closest beacon is at x=10, y=16
        Sensor at x=14, y=17: closest beacon is at x=10, y=16
        Sensor at x=8, y=7: closest beacon is at x=2, y=10
        Sensor at x=2, y=0: closest beacon is at x=2, y=10
        Sensor at x=0, y=11: closest beacon is at x=2, y=10
        Sensor at x=20, y=14: closest beacon is at x=25, y=17
        Sensor at x=17, y=20: closest beacon is at x=21, y=22
        Sensor at x=16, y=7: closest beacon is at x=15, y=3
        Sensor at x=14, y=3: closest beacon is at x=15, y=3
        Sensor at x=20, y=1: closest beacon is at x=15, y=3

    """.trimIndent()
    private val sensorsAndClosestBeacons = (if (debug) testInput.split("\n") else input.asList)
        .filterNotEmpty().associate {
            val split = it.split(" ")
            val sensorX = split[2].substringAfter("=").substringBefore(",").toInt()
            val sensorY = split[3].substringAfter("=").substringBefore(":").toInt()
            val beaconX = split[8].substringAfter("=").substringBefore(",").toInt()
            val beaconY = split[9].substringAfter("=").toInt()
            (sensorX to sensorY) to (beaconX to beaconY)
        }

    override fun part1(): Any {
        val line = if (debug) 10 else 2000000
        return populateMapInLine(line, sensorsAndClosestBeacons).count { it.key.second == line && it.value == CoordinateState.BeaconImpossible }
    }

    enum class CoordinateState {
        Sensor,
        Beacon,
        BeaconImpossible
    }

    private fun populateMap(sensorsAndBeacons: Map<Pair<Int,Int>, Pair<Int,Int>>): Map<Pair<Int,Int>, CoordinateState> {
        val mutableMap: MutableMap<Pair<Int,Int>, CoordinateState> = mutableMapOf()
        for (sb in sensorsAndBeacons) {
            mutableMap.set(sb.key, CoordinateState.Sensor)
            for (coord in sb.key.coordinatesInManhattanDistance(sb.key manhattanDistanceTo sb.value)) {
                mutableMap.set(coord, CoordinateState.BeaconImpossible)
            }
            mutableMap.set(sb.value, CoordinateState.Beacon)
        }
        return mutableMap
    }

    private fun populateMapInLine(line: Int, sensorsAndBeacons: Map<Pair<Int, Int>, Pair<Int, Int>>): Map<Pair<Int,Int>, CoordinateState> {
        val mutableMap: MutableMap<Pair<Int,Int>, CoordinateState> = mutableMapOf()
        for (sb in sensorsAndBeacons) {
            if (debug) println("sensor ${sb.key}, beacon ${sb.value}")
            mutableMap.set(sb.key, CoordinateState.Sensor)
            for (coord in sb.key.coordinatesInManhattanDistanceInY((sb.key manhattanDistanceTo sb.value).also { if (debug) println(it) }, line).also { if (debug) println(it) }) {
                mutableMap.set(coord, CoordinateState.BeaconImpossible)
            }
            mutableMap.set(sb.value, CoordinateState.Beacon)
        }
        return mutableMap
    }

    fun Pair<Int,Int>.coordinatesInManhattanDistanceInY(distance: Int, y: Int): Set<Pair<Int,Int>> {
        val mutableSet = mutableSetOf<Pair<Int,Int>>()
        for (xDiff in 0..distance) {
            /*
            for (yDiff in 0..distance-xDiff) {
                mutableSet.addAll(listOf(
                    (x + xDiff) to (y + yDiff),
                    (x + xDiff) to (y - yDiff),
                    (x - xDiff) to (y + yDiff),
                    (x - xDiff) to (y - yDiff)
                ).filter { it.y == y })
            }*/
            if (y == (this.y - (distance-xDiff)) || y == (this.y + (distance - xDiff))) {
                mutableSet.addAll(/*listOf(
                    (x + xDiff) to y,
                    (x - xDiff) to y
                )*/((this.x-xDiff)..(this.x+xDiff)).map { it to y })
            }
        }
        return mutableSet
    }


}