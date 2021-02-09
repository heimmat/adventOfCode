package Year2015

import Day

class Day16 : Day(2015,16) {
    private val sueFromGift = mapOf<String,Int>(
        "children" to 3,
        "cats" to 7,
        "samoyeds" to 2,
        "pomeranians" to 3,
        "akitas" to 0,
        "vizslas" to 0,
        "goldfish" to 5,
        "trees" to 3,
        "cars" to 2,
        "perfumes" to 1
    )

    private val aunts: Map<Int, Map<String, Int>> = input.asList.filterNotEmpty().map { aunt ->
        val sueNumberString = aunt.drop(4).takeWhile { it.isDigit() }
        val sueNumber = sueNumberString.toInt()
        val properties = aunt.substringAfter(sueNumberString).drop(2)
        sueNumber to properties.split(", ").map { prop ->
            prop.substringBefore(": ") to prop.substringAfter(": ").toInt()
        }.toMap()
    }.toMap()

    override fun part1(): String {
        return aunts.filter {
            matchesGiftSue(it.value)
        }.toList().first().first.toString()
    }

    override fun part2(): String {
        return aunts.filter {
            matchesPart2(it.value)
        }.toList().first().first.toString()
    }

    private fun matchesGiftSue(sue: Map<String,Int>): Boolean {
        return sue.all {
            sueFromGift[it.key] == it.value
        }
    }

    private fun matchesPart2(sue: Map<String, Int>): Boolean {
        val lessThanProps = setOf<String>("pomeranians", "goldfish")
        val greaterThanProps = setOf<String>("cats", "trees")
        return sue.all {
            when (it.key) {
                in lessThanProps -> {
                    sueFromGift[it.key]!!  > it.value
                }
                in greaterThanProps -> {
                    sueFromGift[it.key]!! < it.value
                }
                else -> {
                    sueFromGift[it.key] == it.value
                }
            }
        }
    }
}
