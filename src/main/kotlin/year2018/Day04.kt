package year2018

import Day

class Day04 : Day(2018,4) {
    private val sleepObservations = input.asList.sorted()

    private val testObservations = """[1518-11-01 00:00] Guard #10 begins shift
[1518-11-01 00:05] falls asleep
[1518-11-01 00:25] wakes up
[1518-11-01 00:30] falls asleep
[1518-11-01 00:55] wakes up
[1518-11-01 23:58] Guard #99 begins shift
[1518-11-02 00:40] falls asleep
[1518-11-02 00:50] wakes up
[1518-11-03 00:05] Guard #10 begins shift
[1518-11-03 00:24] falls asleep
[1518-11-03 00:29] wakes up
[1518-11-04 00:02] Guard #99 begins shift
[1518-11-04 00:36] falls asleep
[1518-11-04 00:46] wakes up
[1518-11-05 00:03] Guard #99 begins shift
[1518-11-05 00:45] falls asleep
[1518-11-05 00:55] wakes up""".split("\n")

    fun countMinutesAsleepPerGuard(sleepObservations: List<String>): Map<Int,Int> {
        var activeGuard: Int = -1
        var sleepStart = 0
        var sleepEnd = 0
        var slept = 0
        val minutesAsleep = mutableMapOf<Int,Int>()
        sleepObservations.forEach { observation ->
            val minute = observation.substringAfter(":").substringBefore("]").toInt()
            when {
                observation.endsWith("shift") -> {
                    if (activeGuard != -1 ) minutesAsleep[activeGuard] = minutesAsleep.getOrDefault(activeGuard, 0) + slept
                    activeGuard = observation.substringAfter("#").substringBefore(" begins").toInt()
                    sleepStart = 0
                    sleepEnd = 0
                    slept = 0
                }
                observation.endsWith("asleep") -> {
                    sleepStart = minute
                }
                observation.endsWith("up") -> {
                    sleepEnd = minute
                    slept += sleepEnd - sleepStart
                    sleepStart = 0
                    sleepEnd = 0
                }
            }
        }
        minutesAsleep[activeGuard] = minutesAsleep.getOrDefault(activeGuard, 0) + slept
        return minutesAsleep
    }

    fun minutesAsleepPerGuard(sleepObservations: List<String>): Map<Int, List<Int>> {
        var activeGuard: Int = -1
        var sleepStart = 0
        var sleepEnd = 0
        var slept: List<Int> = listOf()
        val minutesAsleep = mutableMapOf<Int,List<Int>>()
        sleepObservations.forEach { observation ->
            val minute = observation.substringAfter(":").substringBefore("]").toInt()
            when {
                observation.endsWith("shift") -> {
                    if (activeGuard != -1 ) minutesAsleep[activeGuard] = minutesAsleep.getOrDefault(activeGuard, listOf()) + slept
                    activeGuard = observation.substringAfter("#").substringBefore(" begins").toInt()
                    sleepStart = 0
                    sleepEnd = 0
                    slept = listOf()
                }
                observation.endsWith("asleep") -> {
                    sleepStart = minute
                }
                observation.endsWith("up") -> {
                    sleepEnd = minute
                    slept += (sleepStart until sleepEnd).toList()
                    sleepStart = 0
                    sleepEnd = 0
                }
            }
        }
        minutesAsleep[activeGuard] = minutesAsleep.getOrDefault(activeGuard, listOf()) + slept
        return minutesAsleep
    }

    override fun part1(): Any {
        val minutesAsleep = minutesAsleepPerGuard(sleepObservations)
        val sleepiestGuard = minutesAsleep.maxByOrNull { it.value.count() }?.key!!
        val sleepiestMinute = minutesAsleep[sleepiestGuard]?.groupBy { it }?.maxByOrNull { it.value.count() }?.key!!
        return sleepiestMinute * sleepiestGuard
    }

    override fun part2(): Any {
        val minutesAsleep = minutesAsleepPerGuard(sleepObservations)
        val guardMinuteMap = mutableMapOf<Pair<Int,Int>, Int>()
        minutesAsleep.forEach {
            val guard = it.key
            it.value.forEach {
                val minute = it
                val old = guardMinuteMap.getOrDefault(guard to minute, 0)
                guardMinuteMap[guard to minute] = old + 1
            }
        }
        val max = guardMinuteMap.maxByOrNull { it.value }!!
        return max.key.first * max.key.second
    }
}
