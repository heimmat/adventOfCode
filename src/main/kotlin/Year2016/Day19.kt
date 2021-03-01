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

    private fun game(elves: List<Elf>): Int {
        var currentElf = elves.first()
        while (currentElf.next != currentElf) {
            currentElf.presents += currentElf.next!!.presents
            currentElf.next = currentElf.next!!.next
            currentElf = currentElf.next!!
        }
        return currentElf.id


    }

    override fun part1(): Any {
        return game(elves)
    }

    class Elf(val id: Int, var presents: Int = 1, var next: Elf? = null) {
        override fun toString(): String {
            return "Elf $id has $presents presents. Next is ${next?.id}"
        }
    }
}
