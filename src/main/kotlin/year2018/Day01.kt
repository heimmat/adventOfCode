package year2018

import Day

class Day01: Day(2018,1) {

    val frequencyOffsets = input.asList.map { it.toInt() }


    override fun part1(): Any {
        return frequencyOffsets.sum()
    }

    override fun part2(): Any {
        var frequency = 0
        var pointer = 0
        val frequenciesFound = mutableSetOf<Int>()
        while (frequency !in frequenciesFound) {
            frequenciesFound.add(frequency)
            frequency += frequencyOffsets[pointer]
            pointer = (pointer + 1) % frequencyOffsets.size
        }
        return frequency
    }
}

