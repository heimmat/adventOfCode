package Year2015

import Day

class Day18 : Day(2015,18) {
    private val initialMapOfLights = mutableMapOf<Pair<Int,Int>,Boolean>().also {
        input.asList.filterNotEmpty().forEachIndexed { rowIndex, row ->
            row.forEachIndexed { charIndex, char ->
                if (char == '.') {
                    it.put(Pair(rowIndex,charIndex), false)
                }
                else {
                    it.put(Pair(rowIndex,charIndex), true)
                }
            }
        }
    }.toMap()

    override fun part1(): String {
        var state = initialMapOfLights
        repeat(100) {
            state = animate(state)
        }
        return state.values.count { it }.toString()
    }

    override fun part2(): String {
        var state = initialMapOfLights.toMutableMap().also { map ->
            cornersOf(initialMapOfLights).forEach { pair ->
                map[pair] = true
            }
        }.toMap()
        repeat(100) {
            state = animateWithStuck(state)
        }
        return state.values.count { it }.toString()
    }

    private fun neighborsOf(coordinate: Pair<Int,Int>): List<Pair<Int,Int>> {
        val listOfNeighbors = mutableListOf<Pair<Int,Int>>()
        for (row in coordinate.first-1..coordinate.first+1) {
            for (column in coordinate.second-1..coordinate.second+1) {
                if (!(row == coordinate.first && column == coordinate.second)) {
                    listOfNeighbors.add(Pair(row,column))
                }
            }
        }
        return listOfNeighbors
    }

    private fun animate(oldStatus: Map<Pair<Int,Int>, Boolean>): Map<Pair<Int,Int>,Boolean> {
        return oldStatus.map { (key, value) ->
            val neighbors = neighborsOf(key).map { oldStatus.getOrDefault(it, false) }.count { it }
            key to when (value) {
                true -> {
                    if (neighbors in 2..3) true else false
                }
                false -> {
                    if (neighbors == 3) true else false
                }
            }
        }.toMap()
    }

    private fun animateWithStuck(oldStatus: Map<Pair<Int, Int>, Boolean>): Map<Pair<Int,Int>,Boolean> {
        return animate(oldStatus).toMutableMap().also { map ->
            cornersOf(oldStatus).forEach { pair ->
                map[pair] = true
            }
        }.toMap()
    }

    private fun cornersOf(stateMap: Map<Pair<Int,Int>, Boolean>): List<Pair<Int,Int>> {
        val minFirst = stateMap.minOf { it.key.first }
        val maxFirst = stateMap.maxOf { it.key.first }
        val minSecond = stateMap.minOf { it.key.second }
        val maxSecond = stateMap.maxOf { it.key.second }
        return listOf(
            Pair(minFirst,minSecond),
            Pair(minFirst,maxSecond),
            Pair(maxFirst,minSecond),
            Pair(maxFirst,maxSecond)
        )
    }
}
