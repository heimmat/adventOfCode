package year2017

import Day

class Day05 : Day(2017,5) {
    private val testOffsets = listOf(
        0,
        3,
        0,
        1,
        -3
    )

    private val offsets = input.asList.filterNotEmpty().map { it.toInt() }


    override fun part1(): Any {
        val offsets = offsets.toMutableList()
        var pointer = 0
        var steps = 0
        while (pointer in offsets.indices) {
            val tmpPointer = pointer
            pointer = pointer + offsets[pointer]
            offsets[tmpPointer]++
            steps++
        }
        return steps
    }

    override fun part2(): Any {
        val offsets = offsets.toMutableList()
        var pointer = 0
        var steps = 0
        while (pointer in offsets.indices) {
            val tmpPointer = pointer
            val offset = offsets[pointer]
            pointer = pointer + offset
            if (offset >= 3) {
                offsets[tmpPointer]--
            } else {
                offsets[tmpPointer]++
            }
            steps++
        }
        return steps
    }
}
