package Year2015

import Day

class Day17 : Day(2015,17) {
    private val testInput = listOf(20,15,10,5,5)
    private val containers = input.asList.filterNotEmpty().map { it.toInt() }

    private val validCombinations = findCombinations(150, containers)

    override fun part1(): String {
        return validCombinations.size.toString()
    }

    override fun part2(): String {
        val minLength = validCombinations.minOf { it.size }
        return validCombinations.filter {
            it.size == minLength
        }.size.toString()

    }

    private fun findCombinations(target: Int, containers: List<Int>): List<List<Int>> {
        return containers.allCombinations().filter {
            it.sum() == target
        }
    }


}

fun <T> List<T>.allCombinations(): List<List<T>> {
    val combinationCount = 1 shl size
    return (0 until combinationCount).map {
        takeBitmask(it)
    }
}

fun <T> List<T>.takeBitmask(mask: Int): List<T> {
    val mutableList = mutableListOf<T>()
    for (i in 0 until size) {
        if (mask and (1 shl i) > 0) {
            mutableList.add(this[i])
        }
    }
    return mutableList
}
