package Year2016

import Day

class Day19 : Day(2016,19) {
    private val numOfElves = input.asString.trim().toInt()

    private val elves = (1..numOfElves).map {
        Elf(it)
    }.also { it.forEachIndexed { index, elf ->
        if (index == it.lastIndex) {
            elf.next = it[0]
        } else {
            elf.next = it[index + 1]
        }
    } }

    private fun solvePart1(elves: List<Elf>): Int {
        var currentElf = elves.first()
        while (currentElf.next != currentElf) {
            currentElf.presents += currentElf.next!!.presents
            currentElf.next = currentElf.next!!.next
            currentElf = currentElf.next!!
        }
        return currentElf.id


    }

    override fun part1(): Any {
        return solvePart1(elves)
    }

    // https://github.com/tginsberg/advent-2016-kotlin/blob/master/src/main/kotlin/com/ginsberg/advent2016/Day19.kt
    override fun part2(): Any {
        val left = 1..numOfElves/2
        val right = (numOfElves/2 + 1)..numOfElves

        val leftDeque = ArrayDeque(left.toList())
        val rightDeque = ArrayDeque(right.toList())

        while (leftDeque.isNotEmpty() && rightDeque.isNotEmpty()) {
            if (leftDeque.size > rightDeque.size) {
                leftDeque.removeLast()
            } else {
                rightDeque.removeFirst()
            }
            if (rightDeque.isNotEmpty()) leftDeque.addLast(rightDeque.removeFirst())
            if (leftDeque.isNotEmpty()) rightDeque.addLast(leftDeque.removeFirst())

        }
        return if (leftDeque.isNotEmpty()) {
            leftDeque.first()
        } else {
            rightDeque.first()
        }

    }

    class Elf(val id: Int, var presents: Int = 1, var next: Elf? = null) {
        override fun toString(): String {
            return "Elf $id has $presents presents. Next is ${next?.id}"
        }
    }
}
