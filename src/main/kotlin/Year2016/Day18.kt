package Year2016

import Day

class Day18 : Day(2016,18) {

    val initialTraps = input.asString.trim()

    private val safeChar = '.'
    private val trapChar = '^'


    fun String.next(): String {
        return mapIndexed { index, char ->
            if (isTrapInNext(index)) trapChar else safeChar
        }.joinToString("")
    }

    fun String.isTrapInNext(index: Int): Boolean {
        val leftIndex = index - 1
        val centerIndex = index
        val rightIndex = index + 1

        return isTrap(leftIndex) && isTrap(centerIndex) && !isTrap(rightIndex) ||   //Its left and center tiles are traps, but its right tile is not.
                !isTrap(leftIndex) && isTrap(centerIndex) && isTrap(rightIndex) ||  //Its center and right tiles are traps, but its left tile is not.
                isTrap(leftIndex) && !isTrap(centerIndex) && !isTrap(rightIndex) || //Only its left tile is a trap.
                !isTrap(leftIndex) && !isTrap(centerIndex) && isTrap(rightIndex)    //Only its right tile is a trap.

    }

    fun String.isTrap(index: Int): Boolean {
        return if (index in indices) this[index] == trapChar else false
    }

    fun countSafeTiles(length: Int): Int {
        val sequence = generateSequence(initialTraps) { it.next() }
        return sequence.take(length).sumBy {
            it.count { it == safeChar }
        }
    }


    override fun part1(): Any {
        return countSafeTiles(40)
    }

    override fun part2(): Any {
        return countSafeTiles(400000)
    }
}
