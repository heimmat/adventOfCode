package year2017

import Day

class Day25 : Day(2017,25) {

    val testRules = """Begin in state A.
Perform a diagnostic checksum after 6 steps.

In state A:
  If the current value is 0:
    - Write the value 1.
    - Move one slot to the right.
    - Continue with state B.
  If the current value is 1:
    - Write the value 0.
    - Move one slot to the left.
    - Continue with state B.

In state B:
  If the current value is 0:
    - Write the value 1.
    - Move one slot to the left.
    - Continue with state A.
  If the current value is 1:
    - Write the value 1.
    - Move one slot to the right.
    - Continue with state A.""".split("\n")

    val startState = input.asList.first().takeLast(2).first()
    val checksumAfter = input.asList[1].substringAfter("after ").substringBefore(" steps.").also { println(it) }.toInt()
    val rules = parseInput(input.asList)

    override fun part1(): Any {
        val turingMachine = TuringMachine(startState, rules)
        repeat(checksumAfter) {
            turingMachine.tick()
        }
        return turingMachine.checksum
    }

    private fun parseInput(input: List<String>): Map<Char, Map<Boolean, Triple<Boolean,Int,Char>>> {
        return input.filterNotEmpty().drop(2).windowed(9,9).map {
            val state = it[0].takeLast(2).first()
            state to mapOf(
                false to Triple(it[2].getValue(), it[3].getOffset(), it[4].getNextState()),
                true to Triple(it[6].getValue(), it[7].getOffset(), it[8].getNextState())
            )
        }.toMap()
    }

    private fun String.getValue(): Boolean {
        return when {
            endsWith("1.") -> true
            endsWith("0.") -> false
            else -> throw IllegalArgumentException()
        }
    }

    private fun String.getOffset(): Int {
        return when {
            endsWith("left.") -> -1
            endsWith("right.") -> 1
            else -> throw IllegalArgumentException()
        }
    }

    private fun String.getNextState(): Char {
        return takeLast(2).first()
    }

    class TuringMachine(var state: Char, val states: Map<Char, Map<Boolean, Triple<Boolean,Int,Char>>>) {
        private val tape = mutableMapOf<Int, Boolean>()
        val checksum: Int get() = tape.values.count { it }
        var cursor = 0

        fun tick() {
            val rulesForState = states[state] ?: throw IllegalStateException()
            val valueAtCursor = tape.getOrDefault(cursor, false)
            val triple = rulesForState[valueAtCursor] ?: throw IllegalStateException()
            tape[cursor] = triple.first
            cursor += triple.second
            state = triple.third
        }

    }
}
