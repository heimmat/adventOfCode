package year2022

import Day

class Day08(debug: Boolean = false): Day(2022,8,debug) {
    private val testInput = """
        30373
        25512
        65332
        33549
        35390
    """.trimIndent()

    val trees = (if (debug) testInput.split("\n") else input.asList)
        .filterNotEmpty()
        .flatMapIndexed { y, s ->
            s.mapIndexed { x, c ->
                (x to y) to c.digitToInt()
            }
        }
        .toMap()

    override fun part1(): Any {
        return trees.keys.count { trees.isVisible(it) }
    }

    val <T> Map<Pair<Int,Int>,T>.lowestX: Int get() = this.keys.minOf { it.first }
    val <T> Map<Pair<Int,Int>,T>.lowestY: Int get() = this.keys.minOf { it.second }
    val <T> Map<Pair<Int,Int>,T>.highestX: Int get() = this.keys.maxOf { it.first }
    val <T> Map<Pair<Int,Int>,T>.highestY: Int get() = this.keys.maxOf { it.second }

    fun <T> Map<Pair<Int,Int>,T>.neighborsInXAscending(coordinate: Pair<Int, Int>): List<T> {
        return ((coordinate.first + 1)..highestX).map { x ->
            this[x to coordinate.second]!!
        }
    }
    fun <T> Map<Pair<Int,Int>,T>.neighborsInYAscending(coordinate: Pair<Int, Int>): List<T> {
        return ((coordinate.second + 1)..highestY).map { y ->
            this[coordinate.first to y]!!
        }
    }

    fun <T> Map<Pair<Int,Int>,T>.neighborsInXDescending(coordinate: Pair<Int, Int>): List<T> {
        return (0 until (coordinate.first)).map { x ->
            this[x to coordinate.second]!!
        }
    }
    fun <T> Map<Pair<Int,Int>,T>.neighborsInYDescending(coordinate: Pair<Int, Int>): List<T> {
        return (0 until (coordinate.second)).map { y ->
            this[coordinate.first to y]!!
        }
    }

    fun Map<Pair<Int,Int>,Int>.isVisible(coordinate: Pair<Int, Int>): Boolean {
        if (lowestX == lowestY && highestX == highestY) {
            return listOf(
                neighborsInXAscending(coordinate),
                neighborsInYAscending(coordinate),
                neighborsInXDescending(coordinate),
                neighborsInYDescending(coordinate)
            ).any { isVisibleWithNeighbors(coordinate, it) }
        } else {
            throw IllegalArgumentException("Map must be square")
        }

    }

    fun Map<Pair<Int,Int>,Int>.isVisibleWithNeighbors(coordinate: Pair<Int, Int>, neighbors: List<Int>): Boolean {
        val height = this[coordinate]
        return if (height == null) {
            throw IllegalArgumentException("$coordinate out of bounds")
        } else {
            val height: Int = height
            neighbors.all { it < height }

        }
    }



}