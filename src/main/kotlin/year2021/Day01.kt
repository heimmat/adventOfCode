package year2021

import Day

class Day01(): Day(2021, 1) {
    val measurements = input.asList.map { it.toInt() }

    override fun part1(): Any {
        return measurements.windowed(2).count { it.first() < it.last() }
    }

    override fun part2(): Any {
        return measurements.windowed(3).map { it.sum() }.windowed(2).count { it.first() < it.last() }
    }
}