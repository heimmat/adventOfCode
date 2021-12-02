package year2018

import Day
import util.manhattanDistanceTo
import util.toRange
import util.x
import util.y

class Day06 : Day(2018,6) {
    val testInput = listOf(
        "1, 1",
        "1, 6",
        "8, 3",
        "3, 4",
        "5, 5",
        "8, 9"
    )
    val coordinates = input.asList.filterNotEmpty().map {
        val split = it.split(", ").map { it.trim() }
        split[0].toInt() to split[1].toInt()
    }

    override fun part1(): Any {
        val yRange = coordinates.map { it.second }.toRange()
        val xRange = coordinates.map { it.first }.toRange()
        val pointsSearched = mutableMapOf<Pair<Int,Int>, Pair<Int,Int>?>()
        for (y in yRange) {
            for (x in xRange) {
                val point = x to y
                //if (point in coordinates) print("O") else print(".")
                pointsSearched.put(point,point.closestCoordinate())
            }
            //print("\n")
        }
        return coordinates.filterNot { it.isEdge() }.map { coordinate ->
            pointsSearched.count { it.value?.equals(coordinate) ?: false }
        }.maxOf { it }
    }

    override fun part2(): Any {
        var counter = 0
        for (y in coordinates.map { it.y }.toRange()) {
            for (x in coordinates.map { it.x }.toRange()) {
                if ((x to y).inRegion()) counter++
            }
        }
        return counter
    }



    private fun List<Pair<Int,Int>>.corners(): List<Pair<Int,Int>> {
        val xRange = map { it.first }.toRange()
        val yRange = map { it.second }.toRange()
        return listOf(
            xRange.first to yRange.first,
            xRange.endInclusive to  yRange.first,
            xRange.first to yRange.endInclusive,
            xRange.endInclusive to yRange.endInclusive
        )
    }

    private fun Pair<Int,Int>.isEdge(): Boolean {
        return first in coordinates.corners().map { it.first } || second in coordinates.corners().map { it.second }
    }

    private fun Pair<Int,Int>.distances(): List<Pair<Pair<Int,Int>,Int>> {
        return coordinates.map {
            it to this.manhattanDistanceTo(it)
        }
    }

    private fun Pair<Int,Int>.closestCoordinate(): Pair<Int,Int>? {
        val twoClosest = distances().sortedBy { it.second }.take(2)
        return if (twoClosest[0].second == twoClosest[1].second || twoClosest[0].first.isEdge() ) {
            null
        } else {
            twoClosest[0].first
        }

    }

    private fun Pair<Int,Int>.inRegion(): Boolean {
        return distances().sumBy { it.second } < 10000
    }


}
