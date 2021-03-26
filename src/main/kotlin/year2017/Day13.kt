package year2017

import Day

class Day13 : Day(2017,13) {
    val rangeAtDepth: Map<Int,Int> = input.asList.filterNotEmpty().map {
        val (depth,range) = it.split(": ").map { it.toInt() }
        depth to range
    }.toMap()

    override fun part1(): Any {
        return severityOfTripAtTime(0)
    }

    override fun part2(): Any {
       /* return generateSequence(0, Int::inc)
            .filter { delay ->
                rangeAtDepth.none { caughtAtTime(it.key + delay, it.value) }
            }.first()*/

        /*val severities = generateSequence(0 to severityOfTripAtTime(0)) {
            it.first + 1 to severityOfTripAtTime(it.first + 1)
        }
        //return severities.take(20).toList()
        return severities.first { it.second == 0 && !caughtAtTime(it.first, rangeAtDepth[0]!!)}.also { println(!caughtAtTime(it.first, rangeAtDepth[0]!!)) }*/

        var delay = 0
        var passed = false
        while (!passed) {
            val passesFirst = canPassFirst(delay)
            passed = if (passesFirst) {
                severityOfTripAtTime(delay) == 0
            } else {
                passesFirst
            }
            delay++
        }
        return delay - 1

    }

    private fun severityOfTripAtTime(time: Int): Int {
        return rangeAtDepth.map {
            if (caughtAtTime(it.key + time, it.value)) {
                it.key * it.value
            } else {
                0
            }
        }.sum()
    }

    private fun canPassFirst(delay: Int): Boolean {
        return if (rangeAtDepth.containsKey(0)) {
            val range = rangeAtDepth[0]!!
            !caughtAtTime(delay, range)
        } else {
            false
        }
    }

    fun caughtAtTime(time: Int, range: Int): Boolean {
        return positionAtTime(time, range) == 0
    }

    private fun positionAtTime(time: Int, depth: Int): Int {
        //Faster implementation inspired by https://todd.ginsberg.com/post/advent-of-code/2017/day13/
        return time % ((depth-1)*2)
        //return positionsForRange(depth).elementAt(time)
    }



    private fun positionsForRange(range: Int): Sequence<Int> {
        return sequence {
            yieldAll(0 until range)
            yieldAll((1 until range - 1).reversed())
            yieldAll(positionsForRange(range))
        }
    }


}
