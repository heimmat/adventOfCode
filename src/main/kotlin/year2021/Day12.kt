package year2021

import Day

class Day12(debug: Boolean = false): Day(2021,12, debug) {
    //All heavily inspired by https://todd.ginsberg.com/post/advent-of-code/2021/day12/
    private val testInput = """
        start-A
        start-b
        A-c
        A-b
        b-d
        A-end
        b-end
    """.trimIndent().split("\n")

    private fun <T> List<T>.toPair(): Pair<T,T> {
        if (this.size >= 2) {
            return this[0] to this[1]
        } else {
            throw IllegalArgumentException()
        }
    }

    private val connections = (if (debug) testInput else input.asList)
        .map {
            it.split("-").toPair()
        }.flatMap {
            listOf(
                it.first to it.second,
                it.second to it.first
            )
        }.groupBy({it.first}, {it.second})

    private fun traverse(traversalRule: (String, List<String>) -> Boolean, path: List<String> = listOf("start")): List<List<String>> {
        if (path.last() == "end") {
            return listOf(path)
        } else {
            return connections.getValue(path.last())
                .filter { traversalRule(it, path) }
                .flatMap { traverse(traversalRule, path + it) }
        }
    }

    private fun String.isUpperCase(): Boolean = all { it.isUpperCase() }


    private val rulePart1: (name: String, path: List<String>) -> Boolean = { name, path ->
        name.isUpperCase() || name !in path
    }

    private val rulePart2: (name: String, path: List<String>) -> Boolean = { name, path ->
        when {
            name == "start" -> false
            name.isUpperCase() -> true
            name !in path -> true
            else -> path
                .filterNot { it.isUpperCase() }
                .groupBy { it }
                .none { it.value.size == 2}
        }
    }

    override fun part1(): Any {
        return traverse(rulePart1).size
    }

    override fun part2(): Any {
        return traverse(rulePart2).size
    }
}