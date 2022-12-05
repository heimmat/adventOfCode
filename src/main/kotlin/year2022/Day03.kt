package year2022

import Day

class Day03(debug: Boolean = false): Day(2022, 3, debug) {
    private val testInput = """vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw
""".trimIndent()

    val rucksacks = (if (debug) testInput.split("\n") else input.asList)
        .map {
            val len = it.length
            it.take(len/2) to it.drop(len/2).take(len/2)
        }

    override fun part1(): Any {
        return rucksacks.map { rucksack ->
            rucksack.first.first {
                it in rucksack.second
            }
        }.map { it.priority() }.also{
            if (debug) println(it)
        }.sum()

    }

    fun Char.priority(): Int {
        return if (this.isLowerCase()) {
            this - 'a' + 1
        } else if (this.isUpperCase()) {
            26 + (this - 'A' + 1)
        } else {
            throw IllegalArgumentException()
        }
    }

    override fun part2(): Any {
        return rucksacks.map {
            it.first + it.second
        }.windowed(3,3).map { triple ->
            triple.first().first {
                it in triple[1] && it in triple[2]
            }.priority()
        }.sum()
    }

    //operator fun Char.minus(that: Char): Int = this.code - that.code

}