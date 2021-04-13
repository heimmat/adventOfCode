package year2017

import Day

class Day23 : Day(2017,23) {

    private val program = input.asList.filterNotEmpty().map { Command.parseString(it) }

    override fun part1(): Any {
        val computer = Computer()
        var counter = 0
        var pointer = 0
        while (pointer in program.indices) {
            val command = program[pointer]
            if (command.instruction == Instruction.MUL) counter++
            pointer += computer.executeCommand(command)
        }
        return counter
    }

    //Analysis by https://todd.ginsberg.com/post/advent-of-code/2017/day23/
    override fun part2(): Any {
        val a = program.first().y.toInt() * 100 + 100000
        return (a .. a+17000 step 17).count {
            !it.toBigInteger().isProbablePrime(5)
        }
    }

    class Computer {
        private val mutableRegister = mutableMapOf<Char, Int>()
        val register: Map<Char,Int> get() = mutableRegister.toMap()

        private fun valueOf(string: String): Int {
            return when {
                string.all { it.isDigit() || it == '-' } -> {
                    string.toInt()
                }
                string.length == 1 -> {
                    register.getOrDefault(string.first(), 0)
                }
                else -> {
                    throw IllegalArgumentException()
                }
            }
        }

        fun executeCommand(command: Command): Int {
            return when (command.instruction) {
                Instruction.SET -> {
                    mutableRegister[command.x.first()] = valueOf(command.y)
                    1
                }
                Instruction.SUB -> {
                    val currentValue = valueOf(command.x)
                    mutableRegister[command.x.first()] = currentValue - valueOf(command.y)
                    1
                }
                Instruction.MUL -> {
                    val currentValue = valueOf(command.x)
                    mutableRegister[command.x.first()] = currentValue * valueOf(command.y)
                    1
                }
                Instruction.JNZ -> {
                    if (valueOf(command.x) == 0) {
                        1
                    } else {
                        valueOf(command.y)
                    }
                }
            }
        }
    }

    enum class Instruction {
        SET,
        SUB,
        MUL,
        JNZ
    }

    data class Command(val instruction: Instruction, val x: String, val y: String) {
        companion object {
            fun parseString(string: String): Command {
                val split = string.split(" ")
                return Command(Instruction.valueOf(split[0].toUpperCase()), split[1], split[2])
            }
        }
    }
}
