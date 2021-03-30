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

    override fun part1(): Any {
        val bufferAt2017 = getSequence(stepsForward).elementAt(2017)
        val indexOf2017 = bufferAt2017.indexOf(2017)
        return bufferAt2017[(indexOf2017 + 1) % bufferAt2017.size]
    }

    override fun part2(): Any {
        val buffer = getSequence(stepsForward).elementAt(50_000_000)
        val index = buffer.indexOf(0)
        return buffer[(index + 1) % buffer.size]
    }


}
