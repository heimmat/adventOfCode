package Year2016

import Day

class Day06 : Day(2016,6) {
    override fun part1(): Any {
        //assure all input is of same length
        val filteredInput = input.asList.filterNotEmpty()
        val maxLength = filteredInput.maxOf { it.length }
        if (filteredInput.all { it.length == maxLength }) {
            val chars = (0 until maxLength).map { index ->
                filteredInput.map { it[index] }.joinToString("")
            }.map { it.groupBy { it }.map { it.key to it.value.size }.sortedByDescending { it.second }.first() }
            return chars.map { it.first }.joinToString("")
        } else {
            throw IllegalArgumentException()
        }
    }

    override fun part2(): String {
        //assure all input is of same length
        val filteredInput = input.asList.filterNotEmpty()
        val maxLength = filteredInput.maxOf { it.length }
        if (filteredInput.all { it.length == maxLength }) {
            val chars = (0 until maxLength).map { index ->
                filteredInput.map { it[index] }.joinToString("")
            }.map { it.groupBy { it }.map { it.key to it.value.size }.sortedBy { it.second }.first() }
            return chars.map { it.first }.joinToString("")
        } else {
            throw IllegalArgumentException()
        }
    }
}
