package year2017

import Day
import util.Stack

class Day09 : Day(2017,9) {

    //https://todd.ginsberg.com/post/advent-of-code/2017/day9/

    private val cancel = "!.".toRegex()
    private val garbage = "<.*?>".toRegex()
    private val nonGroup = "[^{}]".toRegex()

    private val cleanInput = input.asString.replace(cancel, "")

    override fun part1(): Any {
        return scoreGroups(cleanInput.replace(garbage, "").replace(nonGroup, "").toList())
    }

    override fun part2(): Any {
        return garbage.findAll(cleanInput).map {
            it.value.length - 2
        }.sum()
    }


    private tailrec fun scoreGroups(stream: List<Char>, score: Int = 0, depth: Int = 1): Int {
        return when {
            stream.isEmpty() -> score
            stream.first() == '}' -> scoreGroups(stream.drop(1), score, depth - 1)
            else -> scoreGroups(stream.drop(1), score + depth, depth + 1)
        }
    }

}
