package year2018

import Day

class Day05 : Day(2018,5) {


    override fun part1(): Any {
        return input.asString.trim().collapse().length
    }

    override fun part2(): Any {
        return ('a'..'z').map {
            input.asString.trim().replace("$it", "").replace("$it".toUpperCase(), "")
        }.map {
            it.collapse()
        }.minOf { it.length }
    }

    private fun String.collapse(): String {
        val reactive = ('a'..'z').zip('A'..'Z').map { "${it.first}${it.second}" }.flatMap { listOf(it, it.reversed()) }
        var start = this
        while (reactive.any { it in start }) {
            reactive.forEach {
                start = start.replace(it, "")
            }
        }
        return start
    }
}
