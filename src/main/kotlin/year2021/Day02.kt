package year2021

import Day
import util.plus

class Day02: Day(2021,2) {

    override fun part1(): Any {
        val movements = input.asList.map {
            val split = it.split(" ")
            when (split.first()) {
                "forward" -> {
                    0 to split.last().toInt()
                }
                "down" -> {
                    split.last().toInt() to 0
                }
                "up" -> {
                    0 - split.last().toInt() to 0
                }
                else -> {
                    throw IllegalArgumentException()
                }
            }
        }
        val start = 0 to 0
        var position = start
        movements.forEach {
            position += it
        }
        return position.first * position.second
    }
}