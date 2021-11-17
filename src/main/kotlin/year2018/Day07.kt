package year2018

import Day

class Day07 : Day(2018,7) {
    val testInput = """
        Step C must be finished before step A can begin.
        Step C must be finished before step F can begin.
        Step A must be finished before step B can begin.
        Step A must be finished before step D can begin.
        Step B must be finished before step E can begin.
        Step D must be finished before step E can begin.
        Step F must be finished before step E can begin.""".trimIndent().split("\n")
    val inputSimplified = testInput.map {
        val split = it.split(" ")
        split[7].first() to split[1].first() }


    val tasks = inputSimplified.groupBy { it.first }.map { it.key to it.value.map { it.second }.toSet() }.toMap()

    override fun part1(): Any {
        //find task not in requirements
        val firstTask = inputSimplified.toMap().values.first { it !in inputSimplified.toMap().keys }
        return tasks

    }

}
