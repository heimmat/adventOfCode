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
        val emptyNode = nodes.first { it.used == 0 }
        val wall = nodes.filter { it.drawSymbol(targetNode, dataNode).endsWith("#") }
        val wallMaxX = wall.maxOf { it.x }
        val wallMinX = wall.minOf { it.x }
        val wallMaxY = wall.maxOf { it.y }
        val wallMinY = wall.minOf { it.y }
        val wallXLength = wallMaxX - wallMinX
        val wallYLength = wallMaxY - wallMinY
        val wallEdges = setOf(
            wallMaxX to wallMaxY,
            wallMaxX to wallMinY,
            wallMinX to wallMaxY,
            wallMinX to  wallMinY
        )
        val wallSides = wallEdges.flatMap {
            listOf(it.first + 1 to it.second,
                it.first - 1 to it.second,
                it.first to it.second + 1,
                it.first to it.second - 1)
        }.filter { it in nodes.map { it.coordinates } }.filterNot { it in wall.map { it.coordinates } }
        //find coordinate next to wall in same dimension
        val nextToWall = wallSides.filter {
            if (wallXLength == 0) {
                it.first == wallMaxX
            } else if (wallYLength == 0) {
                it.second == wallMaxY
            } else {
                false
            }
        }
        val distanceEmptyToWall = nodes.first { it.coordinates == nextToWall.first() }.manhattanTo(emptyNode)
        val distanceWallToData = nodes.first { it.coordinates == nextToWall.first() }.manhattanTo(dataNode)
        val distanceDataToTarget = dataNode.manhattanTo(targetNode)
        return distanceEmptyToWall + distanceWallToData + 5 * (distanceDataToTarget - 1)
    }

    //Test with https://github.com/tginsberg/advent-2016-kotlin/blob/master/src/main/kotlin/com/ginsberg/advent2016/Day22.kt
    fun solve2(): Int {
        val maxX = nodes.maxBy { it.x }!!.x
        val wall = nodes.filter { it.size > 250 }.minBy { it.x }!!
        val emptyNode = nodes.first { it.used == 0 }
        var result = Math.abs(emptyNode.x - wall.x) + 1 // Empty around wall X.
        result += emptyNode.y // Empty to top
        result += (maxX - wall.x) // Empty over next to goal
        result += (5 * maxX.dec()) + 1 // Goal back to start
        return result
    }

    private fun findNodePairs(listOfNodes: List<Node>): List<Pair<Node,Node>> {
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

        val neighborCoordinates: List<Pair<Int,Int>> by lazy { listOf(
            x + 1 to y,
            x-1 to y,
            x to y + 1,
            x to y - 1
        ) }

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
