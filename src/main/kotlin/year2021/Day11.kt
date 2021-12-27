package year2021

import Day
import util.plus

class Day11(debug: Boolean = false): Day(2021,11, debug) {
    private val testInput = """
        5483143223
        2745854711
        5264556173
        6141336146
        6357385478
        4167524645
        2176841721
        6882881134
        4846848554
        5283751526
    """.trimIndent().split("\n")

    private var octopi = resetOctopi()

    private fun resetOctopi() = (if (debug) testInput else input.asList)
        .flatMapIndexed { y, s ->
            s.mapIndexed { x, c ->
                (x to y) to Character.getNumericValue(c)
            }
        }.toMap().toMutableMap()

    private fun tick(): Int {
        octopi.replaceAll { _, value ->
            value + 1
        }
        val flashed: MutableSet<Pair<Int,Int>> = mutableSetOf()
        while (flashed != flashers()) {
            val flashers = flashers() - flashed
            flashed.addAll(flashers)
            flashers.forEach { flasher ->
                flasher.neighbors().forEach { neighbor ->
                    octopi.compute(neighbor) { _, v ->
                        v?.plus(1)
                    }
                }
            }
        }
        flashed.forEach {
            octopi.replace(it, 0)
        }
        return flashed.size
    }

    private fun flashers(): Set<Pair<Int,Int>> = octopi.filter { it.value > 9 }.keys

    private fun Pair<Int,Int>.neighbors(): Set<Pair<Int,Int>> {
        return setOf(
            this + (-1 to -1),
            this + (-1 to 0),
            this + (-1 to 1),
            this + (0 to -1),
            this + (0 to 1),
            this + (1 to -1),
            this + (1 to 0),
            this + (1 to 1)
        ).filter { it in octopi.keys }.toSet()
    }

    override fun part1(): Any {
        var count = 0
        if (debug) println("Before any steps:\n${octopi.print()}")
        repeat(100) {
            count += tick()
            if (debug && (it < 10 || it%10 == 9)) println("After Step ${it + 1}:\n${octopi.print()}")
        }
        return count
    }

    private fun MutableMap<Pair<Int,Int>, Int>.print(): String {
        val builder = StringBuilder()
        for (y in 0 until 10) {
            for (x in 0 until 10) {
                builder.append(this[x to y])
            }
            builder.append("\n")
        }
        return builder.toString()
    }

    override fun part2(): Any {
        octopi = resetOctopi()
        var counter = 1
        while (tick() != 100) {
            counter++
        }
        return counter
    }
}