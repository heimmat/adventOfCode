package year2017

import Day

class Day17 : Day(2017,17) {
    val stepsForward = input.asString.trim().toInt()

    private fun getSequence(stepsForward: Int): Sequence<List<Int>> {
        return generateSequence(Triple(mutableListOf(0),  0,0)) {
            val insertPosition = (it.second + stepsForward) % it.first.size + 1
            val newList = it.first.toMutableList()
            newList.add(insertPosition,it.third + 1)
            Triple(newList, insertPosition, it.third + 1)
        }.map { it.first }
    }

    private fun insertPositions(stepsForward: Int): Sequence<Int> {
        return generateSequence(1 to 0) {
            (it.first + 1) to (((it.second + stepsForward) % it.first) + 1)
        }.map { it.second }
    }

    override fun part1(): Any {
        val bufferAt2017 = getSequence(stepsForward).elementAt(2017)
        val indexOf2017 = bufferAt2017.indexOf(2017)
        return bufferAt2017[(indexOf2017 + 1) % bufferAt2017.size]
    }

    override fun part2(): Any {
        return insertPositions(stepsForward).take(50_000_000).indexOfLast { it == 1 }
    }


}
