package year2022

import Day

class Day01(debug: Boolean = false): Day(2022,1, debug) {
    private val testInput = """1000
2000
3000

4000

5000
6000

7000
8000
9000

10000
""".trimIndent()

    val calories = (if (debug) testInput else input.asString)
        .split("\n\n")
        .map {
            it.split("\n").filterNotEmpty()
        }
        .map {
            it.filterNotEmpty().sumBy {
                it.toInt()
            }
        }

    override fun part1(): Any {
        return calories.maxOf { it }
    }

    override fun part2(): Any {
        return calories.sortedDescending().take(3).sumBy { it }
    }

}