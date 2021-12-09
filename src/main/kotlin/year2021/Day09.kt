package year2021

import Day

class Day09(debug: Boolean = false): Day(2021,9, debug) {
    private val testInput = """
        2199943210
        3987894921
        9856789892
        8767896789
        9899965678
    """.trimIndent()

    private val heightMap: Map<Pair<Int, Int>, Int> = (if (debug) testInput.split("\n") else input.asList)
        .mapIndexed { y, s ->
            s.mapIndexed { x, c ->
                (x to y) to Character.getNumericValue(c)
            }
        }
        .flatten()
        .toMap()


    val lows = heightMap
        .filter { entry ->
            entry.key.neighbors.filter { it in heightMap.keys }.all { heightMap[it]!! > entry.value }
        }

    override fun part1(): Any {
        return lows
            .values
            .also { println(it.size) }
            .sumOf { it + 1 }

    }

    override fun part2(): Any {
        return lows
            .map {
                it.key.getBasinNeighborsCount(mutableMapOf())
            }
            .sortedByDescending { it }
            .take(3)
            .reduce { acc, i ->
                acc * i
            }

    }

    val Pair<Int, Int>.neighbors: Set<Pair<Int, Int>>
        get() = setOf(
            this.first + 1 to this.second,
            this.first - 1 to this.second,
            this.first to this.second + 1,
            this.first to this.second -1
        )

    val Pair<Int,Int>.neighborsInHeightMap: Set<Pair<Int,Int>>
        get() = this.neighbors
            .filter { it in heightMap.keys }
            .toSet()

    //Inspired by hendykombat's solution https://www.reddit.com/r/adventofcode/comments/rca6vp/comment/hntnq11/
    fun Pair<Int,Int>.getBasinNeighborsCount(seen: MutableMap<Pair<Int,Int>, Boolean>): Int {
        this.neighborsInHeightMap.forEach {
            if (heightMap[it]!! < 9 ) {
                if (seen[it] == null) {
                    seen[it] = true
                    it.getBasinNeighborsCount(seen)
                }
            }
        }
        return seen.size
    }

}