package Year2016

import Day

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

    class Node(val name: String, val size: Int, val used: Int, val avail: Int, val percent: Int) {
        init {
            if (avail != size - used) println("Avail not valid")
        }
        override fun toString(): String {
            return "$name ${size}T ${used}T ${avail}T ${percent}%"
        }
    }
}
