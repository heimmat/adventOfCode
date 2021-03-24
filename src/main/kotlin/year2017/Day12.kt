package year2017

import Day

class Day12 : Day(2017,12) {
    val sampleInput = """
        0 <-> 2
        1 <-> 1
        2 <-> 0, 3, 4
        3 <-> 2, 4
        4 <-> 2, 3, 6
        5 <-> 6
        6 <-> 4, 5""".trimIndent()

    private val pipeConnections = input.asList.filterNotEmpty().map {
        val (lh,rh) = it.split(" <-> ")
        lh.toInt() to rh.split(", ").map { it.trim().toInt() }
    }.toMap()

    override fun part1(): Any {
        return getConnectedPipes(0).size
    }

    override fun part2(): Any {
        val seenGroups: MutableSet<Set<Int>> = mutableSetOf()
        pipeConnections.forEach { (i, list) ->
            if (seenGroups.none { set -> (list+i).any { it in set } }) {
                seenGroups.add(getConnectedPipes(i))
            }
        }
        return seenGroups.size
    }

    private fun getConnectedPipes(pipe: Int, connectedPipes: Set<Int> = setOf(pipe)): Set<Int> {
        if (pipeConnections.containsKey(pipe)) {
            val directConnections = pipeConnections[pipe]!!
            return directConnections.flatMap {
                if (it !in connectedPipes) {
                    getConnectedPipes(it, connectedPipes + directConnections)
                } else {
                    directConnections
                }
            }.toSet()
        } else {
            throw NoSuchElementException()
        }
    }

}
