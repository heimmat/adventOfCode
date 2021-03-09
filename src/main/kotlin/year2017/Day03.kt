package year2017

import Day
import util.isOdd
import util.square
import kotlin.math.abs
import kotlin.math.sqrt

class Day03 : Day(2017,3) {

    override fun part1(): Any {
        val spiralMemory = SpiralMemory()
        return spiralMemory.distanceFromOrigin(input.asString.trim().toInt())
    }

    class SpiralMemory {
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
            val sideLength = sideLength(index)
            val innerSquareSize = innerSquareSize(index)
            return (0..sideLength.square() - innerSquareSize.square())
                .map { if (it == 0) sideLength.square() else it + innerSquareSize.square() }
                .windowed(sideLength, sideLength - 1)
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
            if (index > 1) {
                return layerOf(index) + distanceFromMiddleOfSide(index)
            } else {
                return 0
            }
        }

        fun neighbors(index: Int): List<Int> {
            //https://oeis.org/A141481
            return emptyList()
        }



    }
}
