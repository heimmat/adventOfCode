package Year2016

import Day
import util.merge

class Day20 : Day(2016,20) {
    val testInput = """5-8
0-2
4-7""".trimIndent().split('\n')

    val blockList = input.asList
        .filterNotEmpty().map {
        val start = it.substringBefore('-').toLong()
        val end = it.substringAfter('-').toLong()
        start..end
    }

    override fun part1(): Any {
        val blockListNext = blockList.map { it.last + 1 }.sorted()
        return blockListNext.first { ip -> blockList.none { ip in it } }
    }

//    override fun part2(): Any {
//        val lastWatched = 4294967295
//        val sorted = blockList.sortedBy { it.first }
//        var notIncluded = 0L
//        sorted.windowed(2).forEach {
//            val difference = it.last().first - (it.first().last + 1)
//            println("$difference Difference between Ranges $it")
//            if (difference > 0) notIncluded += difference
//        }
//        if (sorted.last().last < lastWatched) notIncluded += (lastWatched - (sorted.last().last)).also { println("lastDiff $it") }
//        return notIncluded
//    }

    override fun part2(): Any {
        return 4294967295 + 1 - blockList.merge().also { println(it) }.fold(0L) { acc, closedRange ->
            acc + closedRange.endInclusive + 1 - closedRange.start
        }
    }
}
