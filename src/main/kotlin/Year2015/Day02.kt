package Year2015

import Day
import kotlin.math.min


class Day02: Day(2015,2) {
    private val listOfGifts = input.asList.filter { it != "" }.map { str ->
        val (l,w,h) = str.split("x").map { it.toInt() }
        Gift(l,w,h)
    }
    override fun part1(): String {
        return listOfGifts.sumBy { it.wrappingPaper }.toString()
    }

    override fun part2(): String {
        return listOfGifts.sumBy { it.ribbon }.toString()
    }

    private data class Gift(val l: Int, val w: Int, val h: Int) {
        private val uniqueSides = listOf<Int>(l*w, w*h, h*l)
        val wrappingPaper: Int = 2*uniqueSides.sum() + uniqueSides.minOf { it }
        private val bow = l*w*h
        private val edgesSorted = listOf(l,w,h).sorted()
        val ribbon = 2 * (edgesSorted[0] + edgesSorted[1]) + bow
    }
}