package year2018

import Day

class Day02: Day(2018,2) {
    val String.charOccurrences: Map<Char,Int> get() {
        return groupBy { it }.map { it.key to it.value.size }.toMap()
    }

    fun String.hasRepeatedChar(times: Int): Boolean = charOccurrences.containsValue(times)


    fun filterMatchingChars(one: String, two: String) = one.zip(two).filter { it.first == it.second }.map { it.first }.joinToString("")
    fun countMatchingChars(one: String, two: String) = one.zip(two).count { it.first == it.second }

    override fun part1(): Any {
        return input.asList.count { it.hasRepeatedChar(2) } * input.asList.count { it.hasRepeatedChar(3) }
    }

    override fun part2(): Any {
        return input.asList.map { cand ->
            cand to input.asList.firstOrNull { countMatchingChars(cand, it) + 1 == cand.length }
        }.filter {
            it.second != null
        }.map { filterMatchingChars(it.first, it.second!!) }.first()
    }
}