package year2018

import Day

class Day03 : Day(2018,3) {

    private val claims = fillClaims(input.asList.filterNotEmpty())

    fun fillClaims(claimList: List<String>): Map<Pair<Int,Int>, List<Int>> {
        val claims = mutableMapOf<Pair<Int,Int>, List<Int>>()

        claimList.forEach {
            val id = it.substringBefore(" @").drop(1).toInt()
            val startCoord = it.substringAfter("@ ").substringBefore(",").toInt() to it.substringAfter(",").substringBefore(": ").toInt()
            val diffCoord = it.substringAfter(": ").substringBefore("x").toInt() to it.substringAfter("x").toInt()
            for (x in startCoord.first until startCoord.first + diffCoord.first) {
                for (y in startCoord.second until startCoord.second + diffCoord.second) {
                    val existingList = claims.getOrDefault(x to y, listOf())
                    claims[x to y] = existingList + id
                }
            }
        }
        return claims
    }

    override fun part1(): Any {
        return claims.values.count { it.size > 1 }
    }

    override fun part2(): Any {
        val claimCrashes = claims.filter { it.value.size > 1 }.flatMap { it.value }.toSet()
        return claims.filter { it.value.size == 1 }.filter { it.value.first() !in claimCrashes }.values.toSet().first().first()
    }
}
