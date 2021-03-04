package Year2016

import Day
import kotlin.math.abs

class Day22 : Day(2016,22) {

    private val nodes = input.asList.drop(2).filterNotEmpty().map {
        val split = it.split(Regex("""\s+"""))
        val name = split[0]
        val size = split[1].dropLast(1).toInt()
        val used = split[2].dropLast(1).toInt()
        val avail = split[3].dropLast(1).toInt()
        val percent = split[4].dropLast(1).toInt()
        Node(name, size, used, avail, percent)
    }

    override fun part1(): Any {
        return findNodePairs(nodes).size
    }

    override fun part2(): Any {
        val dataNode = nodes.filter { it.name.endsWith("0") }.maxByOrNull { it.name.substringAfter("x").substringBefore("-").toInt() }!!
        val targetNode = nodes.first { it.name.endsWith("x0-y0") }
        return "\n${drawNodeMap(nodes, targetNode, dataNode)}"
    }

    fun findNodePairs(listOfNodes: List<Node>): List<Pair<Node,Node>> {
        val sortedByAvail = listOfNodes.sortedByDescending { it.avail }
        return sortedByAvail.flatMap { b ->
            listOfNodes.filter { a ->
                a != b && a.used != 0 && a.used <= b.avail
            }.map { a ->
                a to b
            }
        }

    }

    private fun drawNodeMap(nodes: List<Node>, targetNode: Node, dataNode: Node): String {
        val xMax = nodes.maxOf { it.x }
        val yMax = nodes.maxOf { it.y }
        val builder = StringBuilder()
        for (y in 0..yMax) {
            for (x in 0..xMax) {
                val node = nodes.first { it.x == x && it.y == y }
                builder.append(node.drawSymbol(targetNode, dataNode).padStart(3))
            }
            builder.appendLine()
        }
        return builder.toString()
    }

    class Node(val name: String, val size: Int, val used: Int, val avail: Int, val percent: Int) {
        init {
            if (avail != size - used) println("Avail not valid")
        }

        val x = name.substringAfter("x").substringBefore("-").toInt()
        val y = name.substringAfter("y").toInt()
        val coordinates = x to y

        infix fun manhattanTo(other: Node): Int {
            return abs(other.x - x) + abs(other.y - y)
        }

        override fun toString(): String {
            return "$name ${size}T ${used}T ${avail}T ${percent}%"
        }

        fun drawSymbol(targetNode: Node, dataNode: Node): String {
            return when {
                this == targetNode -> "T"
                this == dataNode -> "G"
                used == 0 -> "E"
                percent > 85 -> "#"
                else -> "."
            }
        }
    }
}
