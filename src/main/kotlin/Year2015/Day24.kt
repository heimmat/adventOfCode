package Year2015

import Day

class Day24 : Day(2015,24) {
    val packages = input.asList.filterNotEmpty().map { it.toInt() }

    override fun part1(): String {
        val targetWeight = packages.sum()/3
        var combinationSize = 2
        var combinationFound = false
        var bestCombination: List<Int>? = null
        while (!combinationFound) {
            val combinations = CombinationGenerator(packages,combinationSize).toList().filter { it.sum() == targetWeight }
            if (combinations.isNotEmpty()) {
                combinationFound = true
                bestCombination = combinations.minByOrNull { it.quantumEntanglement() }
            }
            else {
                combinationSize++
            }
        }
        return bestCombination?.quantumEntanglement().toString()
    }

    override fun part2(): String {
        val targetWeight = packages.sum()/4
        var combinationSize = 2
        var combinationFound = false
        var bestCombination: List<Int>? = null
        while (!combinationFound) {
            val combinations = CombinationGenerator(packages,combinationSize).toList().filter { it.sum() == targetWeight }
            if (combinations.isNotEmpty()) {
                combinationFound = true
                bestCombination = combinations.minByOrNull { it.quantumEntanglement() }
            }
            else {
                combinationSize++
            }
        }
        return bestCombination?.quantumEntanglement().toString()
    }
}

//https://stackoverflow.com/a/63986027
class CombinationGenerator<T>(private val items: List<T>, choose: Int = 1) : Iterator<List<T>>, Iterable<List<T>> {
    private val indices = Array(choose) { it }
    private var first = true

    init {
        if (items.isEmpty() || choose > items.size || choose < 1)
            error("list must have more than 'choose' items and 'choose' min is 1")
    }

    override fun hasNext(): Boolean = indices.filterIndexed { index, it ->
        when (index) {
            indices.lastIndex -> items.lastIndex > it
            else -> indices[index + 1] - 1 > it
        }
    }.any()

    override fun next(): List<T> {
        if (!hasNext()) error("AINT NO MORE WHA HAPPEN")
        if (!first) {
            incrementAndCarry()
        } else
            first = false
        return List(indices.size) { items[indices[it]] }
    }

    private fun incrementAndCarry() {
        var carry = false
        var place = indices.lastIndex
        do {
            carry = if ((place == indices.lastIndex && indices[place] < items.lastIndex)
                || (place != indices.lastIndex && indices[place] < indices[place + 1] - 1)) {
                indices[place]++
                (place + 1..indices.lastIndex).forEachIndexed { index, i ->
                    indices[i] = indices[place] + index + 1
                }
                false
            } else
                true
            place--
        } while (carry && place > -1)
    }

    override fun iterator(): Iterator<List<T>> = this
}

fun List<Int>.quantumEntanglement(): Long = fold(1L) { acc, i -> acc * i }

