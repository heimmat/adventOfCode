package Year2015

import Day

class Day14 : Day(2015,14) {
    private val reindeers = input.asList.filterNotEmpty().map {
        val split = it.split(" ")
        Reindeer(split[0], split[3].toInt(), split[6].toInt(), split[13].toInt())
    }

    override fun part1(): String {
        return reindeers.maxByOrNull { it.kilometersTraveledAt(2503) }.also { println(it?.kilometersTraveledAt(2503)) }.toString()
    }

    override fun part2(): String {
        val winnerMap: MutableMap<Reindeer, Int> = mutableMapOf()
        for (i in 1..2503) {
            val winner = reindeers.maxByOrNull { it.kilometersTraveledAt(i) }!!
            val currentValue = winnerMap.getOrDefault(winner, 0)
            winnerMap[winner] = currentValue + 1
        }
        return winnerMap.maxOf { it.value }.toString()
    }

    private data class Reindeer(val name: String, val speed: Int, val flyingDuration: Int, val restingDuration: Int) {
        private val epoch = flyingDuration + restingDuration
        fun kilometersTraveledAt(time: Int): Int {
            val epochsPassed = time/epoch
            val remainingSeconds = time%epoch
            return if (remainingSeconds > flyingDuration) {
                speed * flyingDuration * (epochsPassed + 1)
            } else {
                speed * flyingDuration * epochsPassed + speed * remainingSeconds
            }
        }
    }
}
