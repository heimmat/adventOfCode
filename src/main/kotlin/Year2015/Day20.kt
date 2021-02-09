package Year2015

import Day

class Day20 : Day(2015,20) {
    private val giftTarget = input.asString.trimEnd().toInt()
    private val factorsMap = mutableMapOf<Int,List<Int>>()
    private val houses: Map<Int,Int> by lazy { fillHouses() }

    //Algorithm by https://www.reddit.com/r/adventofcode/comments/3xjpp2/day_20_solutions/cy59zd9
    private fun fillHouses(): Map<Int,Int> {
        val mapOfHouses = mutableMapOf<Int,Int>()
        for (elf in 1..giftTarget/10) {
            for (house in elf..giftTarget/10 step elf) {
                val previousVal = mapOfHouses.getOrDefault(house, 0)
                mapOfHouses[house] = previousVal + 10*elf
            }
        }
        return mapOfHouses
    }

    override fun part1(): String {
        return houses.filter { it.value >= giftTarget }.minOf { it.key }.toString()
    }

    override fun part2(): String {
        val mapOfHouses = mutableMapOf<Int,Int>()
        for (elf in 1..giftTarget/10) {
            val maxIter = minOf(50*elf, giftTarget/10)
            for (house in elf..maxIter step elf) {
                val previousVal = mapOfHouses.getOrDefault(house, 0)
                mapOfHouses[house] = previousVal + 11*elf
            }
        }
        return mapOfHouses.filter { it.value >= giftTarget }.minOf { it.key }.toString()
    }

}
