package year2017

import Day

class Day02: Day(2017,2) {
    private val spreadsheet = input.asList.filterNotEmpty().map {
        it.split(Regex("""\s+""")).map { it.toInt() }
    }

    override fun part1(): Any {
        return spreadsheet.map {
            it.maxOf { it } - it.minOf { it }
        }.sum()
    }

    override fun part2(): Any {
        return spreadsheet.map { row ->
            row.map { cell ->
                row.map { if (cell != it && cell % it == 0) cell / it else 0 }.sum()
            }.sum()
        }.sum()
    }
}