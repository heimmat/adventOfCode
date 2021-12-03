package year2021

import Day

class Day03(debug: Boolean = false): Day(2021,3, debug) {
    private val trainingData = """
        00100
        11110
        10110
        10111
        10101
        01111
        00111
        11100
        10000
        11001
        00010
        01010""".trimIndent()

    val binaryNumbers = (if (debug) trainingData.split("\n") else input.asList)
        .map { it.toCharArray() }
        .also { if (!it.zipWithNext().all { it.first.size == it.second.size }) throw IllegalArgumentException() }

    override fun part1(): Any {
        val size = binaryNumbers.first().size
        val gamma = mutableListOf<Char>()
        val epsilon = mutableListOf<Char>()
        for (i in 0 until size) {
//            val ones = binaryNumbers.map { it[i] }.count { it == '1' }
//            gamma.add(if (ones > binaryNumbers.size/2) '1' else '0')
//            epsilon.add(if (ones > binaryNumbers.size/2) '0' else '1')
            gamma.add(binaryNumbers.mostCommonAtPosition(i))
            epsilon.add(binaryNumbers.leastCommonAtPosition(i))
        }
        return gamma.joinToString("").toInt(2) * epsilon.joinToString("").toInt(2)
    }

    override fun part2(): Any {

        return o2GeneratorRating().joinToString("").toInt(2) * co2ScrubberRating().joinToString("").toInt(2)
    }

    private fun Collection<CharArray>.countOnesZerosAtPosition(i: Int): Pair<Int,Int> {
        val charsAtPosition = this.map { it[i] }
        return charsAtPosition.count { it == '0' } to charsAtPosition.count { it == '1' }
    }

    private fun Collection<CharArray>.mostCommonAtPosition(i: Int): Char {
        val (zeros, ones) = countOnesZerosAtPosition(i)
        return if (ones >= zeros) '1' else '0'
    }

    private fun Collection<CharArray>.leastCommonAtPosition(i: Int): Char {
        val (zeros, ones) = countOnesZerosAtPosition(i)
        return if (ones >= zeros) '0' else '1'
    }

    fun co2ScrubberRating(): CharArray {
        val size = binaryNumbers.first().size
        var remainingCandidates = binaryNumbers
        for (i in 0 until size) {
            val leastCommon = remainingCandidates.leastCommonAtPosition(i)
            if (debug) println("leastCommon at pos $i: ${leastCommon}")
            remainingCandidates = remainingCandidates.filter {
                it[i] == leastCommon
            }
            if (debug) println("remainingCandidates: ${remainingCandidates.map { it.joinToString("") }}")
            if (remainingCandidates.size == 1) break
        }
        return remainingCandidates.first()
    }

    fun o2GeneratorRating(): CharArray {
        val size = binaryNumbers.first().size
        var remainingCandidates = binaryNumbers
        for (i in 0 until size) {
            val mostCommon = remainingCandidates.mostCommonAtPosition(i)
            if (debug) println("mostCommon at pos $i: ${mostCommon}")
            remainingCandidates = remainingCandidates.filter {
                it[i] == mostCommon
            }
            if (debug) println("remainingCandidates: ${remainingCandidates.map { it.joinToString("") }}")
            if (remainingCandidates.size == 1) break
        }
        return remainingCandidates.first()
    }



}