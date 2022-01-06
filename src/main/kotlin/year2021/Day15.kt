package year2021

import Day
import util.square
import java.util.*

class Day15(debug: Boolean = false): Day(2021, 15, debug) {
    private val testInput = """
        1163751742
        1381373672
        2136511328
        3694931569
        7463417111
        1319128137
        1359912421
        3125421639
        1293138521
        2311944581
    """.trimIndent()

    val riskMap = (if (debug) testInput.split("\n") else input.asList)
        .mapIndexed { y, s ->
            s.mapIndexed { x, c ->
                (x to y) to Character.getNumericValue(c)
            }
        }.flatten()
        .toMap()

    val riskArray = (if (debug) testInput.split("\n") else input.asList)
        .map {
            it.map { Character.getNumericValue(it) }
        }

    fun Map<Pair<Int,Int>, Int>.neighborsOf(coordinate: Pair<Int,Int>): List<Pair<Int,Int>> {
        return coordinate.neighbors().filter { it in this.keys }
    }

    fun Pair<Int,Int>.neighbors() = listOf(
        this.first - 1 to this.second,
        this.first + 1 to this.second,
        this.first to this.second - 1,
        this.first to this.second + 1
    )

    fun Map<Pair<Int,Int>, Int>.riskOfPath(path: Collection<Pair<Int,Int>>): Int = path.drop(1).map {
        this.getValue(it)
    }.sum()

    override fun part1(): Any {
        val lowerRight = riskMap.keys.maxByOrNull { it.first + it.second }
//        val path = mutableListOf<Pair<Int,Int>>(0 to 0)
//        while (path.last() != lowerRight) {
//            val sortedNeighbors = riskMap
//                .neighborsOf(path.last())
//                .sortedBy { riskMap.getValue(it) }
//                //.sortedBy { weighted(riskMap.riskOfPath(path + it) , lowerRight!!.manhattanDistanceTo(it)) }
//            //val bestGuess = sortedNeighbors.firstOrNull() { it !in path } ?: sortedNeighbors.minByOrNull { riskMap[it]!! }!!
//            val bestGuess = sortedNeighbors.firstOrNull { it !in path } ?: sortedNeighbors.minByOrNull { weighted(riskMap.riskOfPath(path + it), it.manhattanDistanceTo(lowerRight!!)) }!!
//
//
//            println("bestGuess is $bestGuess")
//            path.add(bestGuess)
//        }
//
//        return riskMap.riskOfPath(path)

        return riskArray.traverse(lowerRight!!).totalRisk
    }

    fun weighted(risk: Int, distance: Int) = risk - distance.square()


    //Adapted from  https://todd.ginsberg.com/post/advent-of-code/2021/day15/
    private inner class Traversal(val riskMap: Map<Pair<Int,Int>, Int>, val position: Pair<Int,Int>, val pathTaken: List<Pair<Int,Int>>): Comparable<Traversal> {
        val totalRisk: Int get() = riskMap.riskOfPath(pathTaken + position)
        override fun compareTo(other: Traversal): Int = this.totalRisk - other.totalRisk

        fun neighbors(): List<Traversal> {
            return riskMap.neighborsOf(this.position).map {
                Traversal(riskMap, it, pathTaken + position)
            }
        }
    }

    private inner class SlimTraversal(val position: Pair<Int, Int>, val totalRisk: Int): Comparable<SlimTraversal> {
        override fun compareTo(other: SlimTraversal): Int = this.totalRisk - other.totalRisk
    }

    private fun Map<Pair<Int,Int>,Int>.traverse(end: Pair<Int,Int>, start: Pair<Int,Int> = 0 to 0): Traversal {
        val toBeEvaluated = PriorityQueue<Traversal>().apply { add(Traversal(this@traverse, 0 to 0, listOf())) }
        val visited = mutableSetOf<Pair<Int,Int>>()

        while (toBeEvaluated.isNotEmpty()) {
            val thisPlace = toBeEvaluated.poll()
            if (thisPlace.position == end) {
                return thisPlace
            }
            if (thisPlace.position !in visited) {
                visited.add(thisPlace.position)
                /*
                this.neighborsOf(thisPlace.position)
                    .forEach { toBeEvaluated.offer(Traversal(this, it, thisPlace.pathTaken + thisPlace.position)) }
                */
                thisPlace.neighbors().forEach {
                    toBeEvaluated.offer(it)
                }
            }

        }
        error("No path to destination")

    }

    private val Map<Pair<Int,Int>, Int>.lowerRight: Pair<Int,Int> get() = keys.maxOf { it.first } to keys.maxOf { it.second }
    private val Map<Pair<Int,Int>, Int>.validMax: Pair<Int,Int> get() = (lowerRight.first + 1) * 5 - 1 to (lowerRight.second + 1) * 5 - 1


    fun List<List<Int>>.valueOf(coordinate: Pair<Int, Int>): Int {
        return if (coordinate.second < this.size && coordinate.first < this.first().size) {
            this[coordinate.second][coordinate.first]
        } else {
            val factorX = coordinate.first/this.first().size
            val factorY = coordinate.second/this.size
            val coordinateNormalized = coordinate.first % this.first().size to  coordinate.second % this.size
            val originalValue = valueOf(coordinateNormalized)
            val calculatedValue =  (originalValue + factorX + factorY) % 9
            if (calculatedValue == 0) 9 else calculatedValue
        }

    }

    private fun List<List<Int>>.traverse(end: Pair<Int,Int>, start: Pair<Int,Int> = 0 to 0, extendedMap: Boolean = false): SlimTraversal {
        val toBeEvaluated = PriorityQueue<SlimTraversal>().apply { add(SlimTraversal(0 to 0, 0)) }
        val visited = mutableSetOf<Pair<Int,Int>>()

        while (toBeEvaluated.isNotEmpty()) {
            val thisPlace = toBeEvaluated.poll()
            if (thisPlace.position == end) {
                return thisPlace
            }
            if (thisPlace.position !in visited) {
                visited.add(thisPlace.position)

                this.neighborsOf(thisPlace.position, mapScale = if (extendedMap) 5 else 1)
                    .forEach { toBeEvaluated.offer(SlimTraversal(it, thisPlace.totalRisk + this.valueOf(it))) }

            }

        }
        error("No path to destination")

    }

    fun List<List<Int>>.neighborsOf(coordinate: Pair<Int, Int>, mapScale: Int = 1): List<Pair<Int,Int>> {

        return coordinate.neighbors().filter {
            it.first >= 0 && it.first < this.first().size * mapScale && it.second >= 0 && it.second < this.size * mapScale
        }
    }

    override fun part2(): Any {
        return riskArray.traverse(riskMap.validMax, extendedMap = true).totalRisk
    }
}