package year2022

import Day
import util.Stack

class Day05(debug: Boolean = false): Day(2022,5, debug) {
    private val testInput = """
    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3 

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2
    """.trimIndent()

    private val stacks: List<Stack<Char>> get() {
        val input = (if (debug) testInput else input.asString)
        val split = input.split("\n\n")
        val stacks = mutableMapOf<Int, Stack<Char>>()
        split[0].dropLast(1).split("\n").forEach {
            val length = it.length
            for (index in 1 until length step 4) {
                if (it[index].isLetter()) {
                    val stack = stacks.getOrDefault(index, Stack())
                    stack.push(it[index])
                    stacks[index] = stack
                }
            }
        }
        return stacks.toList().sortedBy { it.first }.map { it.second.reverse() }.also { if (debug) println(it) }
    }


    private val instructions = (if (debug) testInput else input.asString)
        .substringAfter("\n\n")
        .split("\n")
        .filterNotEmpty()
        .map { Instruction.parse(it) }

    override fun part1(): Any {
        val stacks = stacks
        for (inst in instructions) {
            repeat(inst.amount) {
                if (debug) println("move ${stacks[inst.from - 1].peek()} from stack ${inst.from} to stack ${inst.to}")
                stacks[inst.to - 1].push(stacks[inst.from - 1].pop()!!)
            }
        }
        return stacks.map { it.peek() }.joinToString("")
    }

    override fun part2(): Any {
        if (debug) println(stacks)
        val stacks  = stacks
        for (inst in instructions) {
            if (debug) println(inst)
            val buffer = Stack<Char>()
            repeat(inst.amount) {
                if (debug) println("will pull ${stacks[inst.from - 1].peek()}")
                buffer.push(stacks[inst.from - 1].pop()!!)
            }
            while (buffer.hasMore()) {
                stacks[inst.to - 1].push(buffer.pop()!!)
            }
        }
        return stacks.map { it.peek() }.joinToString("")
    }

    private fun <T> Stack<T>.reverse() = Stack(this.toList().asReversed())

    data class Instruction(val amount: Int, val from: Int, val to: Int) {
        companion object {
            fun parse(s: String): Instruction {
                val split = s.split(" ")
                val amount = split[1].toInt()
                val from = split[3].toInt()
                val to = split[5].toInt()
                return Instruction(amount, from, to)
            }
        }
    }

}