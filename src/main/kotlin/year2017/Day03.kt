package year2017

import Day
import util.isOdd
import util.plus
import util.square
import kotlin.math.abs
import kotlin.math.sqrt

class Day03 : Day(2017,3) {

    override fun part1(): Any {
        val spiralMemory = SpiralMemory()
        return spiralMemory.distanceFromOrigin(input.asString.trim().toInt())
    }

    override fun part2(): Any {
        val spiralMemory = SpiralMemory()
        val values = mutableMapOf<Pair<Int,Int>, Int>()
        var index = 1
        var value = 1
        while (value < input.asString.trim().toInt()) {
            //println("\nindex is $index")
            val coordinate = spiralMemory.coordinate(index)
            //println("coordinate $coordinate")
            val neighbors = spiralMemory.neighbors(index)
            //println("neighbors $neighbors")
            val neighborValues = neighbors.mapNotNull {
                values[it]
            }
            //println("neighborValues $neighborValues")
            if (neighborValues.isNotEmpty()) {
                value = neighborValues.sum()
            }
            values[coordinate] = value
            index++
        }
        //println(values)
        return value
    }


    class SpiralMemory {

        private val coordinateCache = mutableMapOf<Int, Pair<Int,Int>>()

        private val layers = generateSequence(1) {
            it + 2
        }

        private fun finishedSquareSize(index: Int): Int {
            if (index > 0) {
                val root = sqrt(index.toDouble()).toInt()
                return if (root.isOdd) {
                    root
                } else {
                    root - 1
                }
            } else {
                throw IllegalArgumentException("Index must be greater than 0")
            }
        }

        private fun sideLength(index: Int): Int {
            val squareSize = finishedSquareSize(index)
            return if (squareSize.square() != index) {
                squareSize + 2
            } else {
                squareSize
            }

        }
        private fun layerOf(index: Int): Int = layers.indexOf(sideLength(index))

        private fun distanceFromLowerRightCorner(index: Int): Int {
            val squareSize = finishedSquareSize(index)
            val lowerRightCorner = squareSize * squareSize
            return index - lowerRightCorner
        }

        private fun sides(index: Int): List<List<Int>> {
            return if (index == 1) {
                emptyList()
            } else {
                val sideLength = sideLength(index)
                val innerSquareSize = innerSquareSize(index)
                (0..sideLength.square() - innerSquareSize.square())
                    .map { if (it == 0) sideLength.square() else it + innerSquareSize.square() }
                    .windowed(sideLength, sideLength - 1)
            }

        }

        private fun innerSquareSize(index: Int) = sideLength(index) - 2

        private fun middleOfSides(index: Int): List<Int> {
            return sides(index).map {
                it[it.size/2]
            }
        }

        private fun distanceFromMiddleOfSide(index: Int): Int {
            return middleOfSides(index)
                .map {
                abs(index - it)
            }.minOf { it }
        }

        fun distanceFromOrigin(index: Int): Int {
            return if (index > 1) {
                layerOf(index) + distanceFromMiddleOfSide(index)
            } else {
                0
            }
        }

        /**
         * Returns the position of the index in layer.
         * @return first: index of side, second: index of position
         */
        private fun positionInLayer(index: Int): Pair<Int,Int> {
            val sides = sides(index)
            val sideIndex = sides.indexOfFirst { index in it }
            val positionIndex = sides[sideIndex].indexOf(index)
            return sideIndex to positionIndex
        }

        fun coordinate(index: Int): Pair<Int,Int> {
            return coordinateCache.getOrPut(index) {
                calculateCoordinate(index)
            }
        }

        private fun calculateCoordinate(index: Int): Pair<Int,Int> {
            if (index == 1) {
                return 0 to 0
            } else {
                val layer = layerOf(index)
                val positionInLayer = positionInLayer(index)
                when (positionInLayer.first) {
                    0 -> {
                        val posCorner = layer to -layer
                        return posCorner.first to posCorner.second + positionInLayer.second
                    }
                    1 -> {
                        val posCorner = layer to layer
                        return posCorner.first - positionInLayer.second to posCorner.second
                    }
                    2 -> {
                        val posCorner = -layer to layer
                        return posCorner.first to posCorner.second - positionInLayer.second
                    }
                    3 -> {
                        val posCorner = -layer to -layer
                        return posCorner.first + positionInLayer.second to posCorner.second
                    }
                    else -> throw IllegalArgumentException("Something went wrong.")
                }
            }

        }

        fun neighbors(index: Int): List<Pair<Int,Int>> {
            //https://oeis.org/A141481
            val coordinate = coordinate(index)
            val potentialNeighbors = listOf(
                1 to -1,
                1 to 0,
                1 to 1,
                0 to -1,
                0 to 1,
                -1 to -1,
                -1 to 0,
                -1 to 1,
            ).map { it + coordinate }
            return potentialNeighbors.filter {
                it in prevNeighbors(index)
            }
        }

        private fun prevNeighbors(index: Int): List<Pair<Int,Int>> {
            return (1 until index).map {
                coordinate(it)
            }
        }



    }
}
