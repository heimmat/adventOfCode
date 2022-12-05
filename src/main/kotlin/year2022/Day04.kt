package year2022

import Day

class Day04(debug: Boolean = false): Day(2022,4, debug) {
    private val testInpug = """
        2-4,6-8
        2-3,4-5
        5-7,7-9
        2-8,3-7
        6-6,4-6
        2-6,4-8
    """.trimIndent()
    private val assignments = (if (debug) testInpug.split("\n") else input.asList)
        .map { it.split(",")
            .map {
                it.substringBefore("-").toInt()..it.substringAfter("-").toInt()
            }
        }.toPairs()

    override fun part1(): Any {
        return assignments.count {
            it.first.fullyContains(it.second) || it.second.fullyContains(it.first)
        }
    }

    override fun part2(): Any {
        return assignments.count {
            it.first.overlaps(it.second)
        }
    }

    fun <T> List<List<T>>.toPairs(): List<Pair<T,T>> {
        return if (this.all { it.size >= 2 }) {
            this.map { it.first() to it.drop(1).first() }
        } else {
            throw IndexOutOfBoundsException()
        }
    }

    fun IntRange.fullyContains(that: IntRange): Boolean {
        return this.first <= that.first && this.last >= that.last
    }

    fun IntRange.overlaps(that: IntRange): Boolean {
        return this.any { it in that }
    }
}