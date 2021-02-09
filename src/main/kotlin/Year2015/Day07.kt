package Year2015

import Day

class Day07 : Day(2015,7) {
    val testInput = "123 -> x\n456 -> y\nx AND y -> d\nx OR y -> e\nx LSHIFT 2 -> f\ny RSHIFT 2 -> g\nNOT x -> h\nNOT y -> i"
    private val listOfInstructions = input.asList.filter { it != "" }.map {
        Instruction.fromString(it)
    }

    private val signals = mutableMapOf<String, Int>()

    override fun part1(): String {
        return valueOfWire("a").toString()
    }

    override fun part2(): String {
        val instructions = listOfInstructions.toMutableList().also {
            it.removeAt(it.indexOfFirst { it.target == "b" })
            it.add(Instruction("b", Command.ASSIGN, listOf(part1())))
        }.toList()
        signals.clear()
        return valueOfWire("a", instructions).toString()

    }

    private fun valueOfWire(wire: String, listOfInstructions: List<Instruction> = this.listOfInstructions): Int {
        return if (signals.containsKey(wire)) {
            signals[wire]!!
        }
        else {
            val instruction = listOfInstructions.find { it.target == wire }
            if (instruction == null) {
                throw NoSuchElementException(wire)
            }
            else {
                val parameters = instruction.parameters.map {
                    if (it.all { it.isDigit() }) it.toInt() else valueOfWire(it, listOfInstructions)
                }
                when (instruction.command) {
                    Command.ASSIGN -> signals[instruction.target] = parameters.first()
                    Command.NOT -> signals[instruction.target] = 65536+parameters.first().inv()
                    Command.AND -> signals[instruction.target] = (parameters.first() and parameters.last())%65536
                    Command.OR -> signals[instruction.target] = (parameters.first() or parameters.last())%65536
                    Command.LSHIFT -> signals[instruction.target] = (parameters.first() shl parameters.last())%65536
                    Command.RSHIFT -> signals[instruction.target] = (parameters.first() shr parameters.last())%65536
                }
                signals[instruction.target]!!
            }
        }
    }

    private fun parameterValue(parameter: String): Int {
        return if (parameter.all { it.isDigit() }) {
            parameter.toInt()
        } else {
            signals.getOrDefault(parameter, 0)
        }
    }



    data class Instruction(val target: String, val command: Command, val parameters: List<String>) {
        companion object {
            fun fromString(string: String): Instruction {
                val (lh,rh) = string.split(" -> ")
                val target = rh
                val splitLh = lh.split(" ")
                val command: Command
                val parameters: List<String>
                when (splitLh.count()) {
                    1 -> {
                        command = Command.ASSIGN
                        parameters = listOf(splitLh.first())
                    }
                    2 -> {
                        command = Command.valueOf(splitLh.first())
                        parameters = listOf(splitLh.last())
                    }
                    3 -> {
                        command = Command.valueOf(splitLh[1])
                        parameters = listOf(splitLh[0], splitLh[2])
                    }
                    else -> {
                        throw IllegalArgumentException()
                    }
                }
                return Instruction(target, command, parameters)
            }
        }
    }

    enum class Command {
        ASSIGN,
        AND,
        OR,
        NOT,
        LSHIFT,
        RSHIFT
    }


}
