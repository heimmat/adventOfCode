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

    val components = input.asList
        .map { it.split("/").map { it.toInt() } }
        .map { Component(it[0], it[1]) }

    override fun part1(): Any {
        return findBridges(components, emptyList(), 0).maxOf { it.sumBy { it.strength } }
    }

    override fun part2(): Any {
        return findBridges(components).groupBy { it.size }.maxByOrNull { it.key }?.value?.maxOf { it.sumBy { it.strength } } ?: "null"
    }

    fun findMatches(components: List<Component>, bridge: List<Component>, port: Int): List<Component> {
        return components.filterNot { it in bridge }
            .filter { it.fits(port) }
    }

    fun findBridges(components: List<Component>, bridge: List<Component> = emptyList(), port: Int = 0): List<List<Component>> {
        val matches = findMatches(components, bridge, port)
        return if (matches.isEmpty()) {
            listOf(bridge)
        } else {
            matches.flatMap {
                findBridges(components, bridge + it, it.oppositeSide(port))
            }
        }
    }


    class Component(val x: Int, val y: Int) {
        fun fits(port: Int) = x == port || y == port
        val strength = x + y

        fun oppositeSide(port: Int): Int {
            return when {
                port == x -> y
                port == y -> x
                else -> throw IllegalArgumentException()
            }
        }

        override fun toString(): String {
            return "$x/$y"
        }
    }

}
