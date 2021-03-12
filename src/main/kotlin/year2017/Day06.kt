package year2017

import Day

class Day06 : Day(2017,6) {
    private val testMemory = listOf(0,2,7,0)
    private val initialMemory = input.asString.split(Regex("""\s+""")).filterNotEmpty().map { it.toInt() }

    private val seenStates by lazy { runsUntilSeenBefore() }

    override fun part1(): Any {
        return seenStates.first.size
    }

    override fun part2(): Any {
        return seenStates.first.size - seenStates.first.indexOf(seenStates.second)
    }

    private fun runsUntilSeenBefore(): Pair<Set<List<Int>>, List<Int>> {
        val memoryReallocator = MemoryReallocator(initialMemory)
        val seenStates = mutableSetOf<List<Int>>()
        var currentState: List<Int> = initialMemory
        var runs = 0
        do {
            runs++
            seenStates.add(currentState)
            memoryReallocator.reallocate()
            currentState = memoryReallocator.memory
        } while (currentState !in seenStates)
        return seenStates to currentState
    }




    class MemoryReallocator(initialMemory: List<Int>) {
        var memory: List<Int> = initialMemory
            private set

        val size: Int = memory.size


        fun reallocate() {
            memory = redistributedMemory()

        }

        private fun redistributedMemory(): List<Int> {
            return memory.zip(distributionOfHighest()) {a: Int, b: Int ->
                a + b
            }
        }

        private fun distributionOfHighest(): List<Int> {
            val maxValue = memory.maxOf { it }
            val indexOfMax = memory.indexOf(maxValue)
            val itemsOnAll = maxValue/size
            val rem = maxValue%size
            val basicDistribution = memory.mapIndexed { index, int ->
                if (index == indexOfMax) itemsOnAll - maxValue else itemsOnAll
            }.toMutableList()
            var remLeft = rem
            var currentIndex = (indexOfMax + 1) % size
            while (remLeft > 0) {
                basicDistribution[currentIndex]++
                currentIndex = (currentIndex + 1)% size
                remLeft--
            }
            return basicDistribution
        }
    }
}
