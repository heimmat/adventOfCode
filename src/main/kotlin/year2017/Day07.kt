package year2017

import Day

class Day07 : Day(2017,7) {
    private val nodes = input.asList.filterNotEmpty().map {
        val lh = it.substringBefore(" -> ")
        val name = lh.substringBefore(" ")
        val weight = lh.substringAfter("(").substringBefore(")").toInt()
        val children = it.substringAfter(" -> ", "").split(", ")
        name to Node(name, weight, children)
    }.toMap()

    override fun part1(): Any {
        return nodes.filter { node ->
            nodes.values.map { it.children }.none { node.key in it }
        }.entries.first().key
    }

    data class Node(val name: String, val weight: Int, val children: List<String> = emptyList())
}
