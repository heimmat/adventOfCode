package year2017

import Day
import java.math.BigInteger

class Day14 : Day(2017,14) {
    val keyString = input.asString.trim()//"flqrgnkx"

    override fun part1(): Any {

        val hasher = KnotHasher()
        val hashes = (0..127).map {
            hasher.hashString("$keyString-$it")
        }
        val hashesInBinary = hashes.map {
            BigInteger(it, 16).toString(2).padStart(128,'0')
        }
        return hashesInBinary.sumBy {
            it.count { it == '1' }
        }
    }

}
