package year2017

import Day
import java.math.BigInteger

class Day14 : Day(2017,14) {
    val keyString = input.asString.trim()//"flqrgnkx"

    val hashes = hashesInBinary()
    val grid = hashes.map { it.map { it }.toTypedArray() }.toTypedArray()

    override fun part1(): Any {
        return hashes.sumBy {
            it.count { it == '1' }
        }
    }

    private fun hashesInBinary(): List<String> {
        val hasher = KnotHasher()
        val hashes = (0..127).map {
            hasher.hashString("$keyString-$it")
        }
        val hashesInBinary = hashes.map {
            BigInteger(it, 16).toString(2).padStart(128,'0')
        }
        return hashesInBinary
    }

    override fun part2(): Any {
        //Adopted from https://todd.ginsberg.com/post/advent-of-code/2017/day14/
        var groups = 0
        grid.forEachIndexed { y, row ->
            row.forEachIndexed { x, spot ->
                if (spot == '1') {
                    groups += 1
                    markNeighbors(x, y)
                }
            }
        }
        return groups
    }

    private fun markNeighbors(x: Int, y: Int) {
    if (grid[y][x] == '1') {
        grid[y][x] = '0'
        getActiveNeighbors(x to y).forEach {
            markNeighbors(it.first, it.second)
        }
    }
}

    val Pair<Int,Int>.isActive: Boolean get() = hashes[second][first] == '1'

    private fun getActiveNeighbors(coordinate: Pair<Int, Int>) = getNeighbors(coordinate).filter { it.isActive }.toSet()

    private fun getNeighbors(coordinate: Pair<Int,Int>): Set<Pair<Int,Int>> {
        return setOf(
            coordinate.first - 1 to coordinate.second,
            coordinate.first + 1 to coordinate.second,
            coordinate.first to coordinate.second - 1,
            coordinate.first to coordinate.second + 1
        ).filter { it.first in 0..127 }
            .filter { it.second in 0..127 }
            .toSet()
    }

}
