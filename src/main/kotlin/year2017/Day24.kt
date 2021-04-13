package year2017

import Day

class Day24 : Day(2017,24) {

    val testComponents = listOf(
        "0/2",
        "2/2",
        "2/3",
        "3/4",
        "3/5",
        "0/1",
        "10/1",
        "9/10"
    )

    val components = testComponents//input.asList
        .map { it.split("/").map { it.toInt() } }
        .map { it[0] to it[1] }

    private val startingComponents = components.filter { it.first == 0 }

    fun findMatches(bridge: Set<Pair<Int,Int>>): List<Pair<Int,Int>> {
        return components.filterNot { it in bridge }.filter { it.first == bridge.last().second || it.second == bridge.last().second }
    }

    fun findBridges(bridge: Set<Pair<Int, Int>>): List<Set<Pair<Int,Int>>> {
        println("bridge is $bridge")
        val openMatches = findMatches(bridge)
        if (openMatches.isNotEmpty()) {
            return openMatches.flatMap {
                findBridges(bridge + it)
            }
        } else {
            return listOf(bridge)
        }

    }

    override fun part1(): Any {
        return startingComponents.flatMap {
            findBridges(setOf(it))
        }
    }

    val Pair<Int,Int>.strength: Int get() = first + second
}
