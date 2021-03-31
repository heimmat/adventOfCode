package year2017

import Day

class Day18 : Day(2017,18) {

    val testProgram = """set a 1
                        add a 2
                        mul a a
                        mod a 5
                        snd a
                        set a 0
                        rcv a
                        jgz a -1
                        set a 1
                        jgz a -2"""
        .trimIndent()
        .split("\n")
        .map { SoundComputer.Command.parseString(it.trim()) }

    val program = input.asList.filterNotEmpty().map {
        SoundComputer.Command.parseString(it)
    }

    override fun part1(): Any {
        return SoundComputer().runUntilRecovered(program)
    }

    class SoundComputer {

        private val register = mutableMapOf<Char, Long>()

        private val soundBuffer = mutableListOf<Long>()

        private val recovered = mutableListOf<Long>()

        fun runUntilRecovered(program: List<Command>): Long {
            var pointer = 0
            while (recovered.isEmpty() && pointer in program.indices) {
                pointer += executeCommand(program[pointer])
            }
            return soundBuffer.last()
        }

        private fun valueOf(string: String): Long {
            return if (string.length > 1) {
                //Assume it is a number
                string.toLong()
            } else {
                val char = string.first()
                if (char.isDigit()) {
                    string.toLong()
                } else {
                    register.getOrDefault(char, 0)
                }
            }
        }

        /**
         * Returns the offset of the next command
         */
        fun executeCommand(command: Command): Int {
            when (command.instruction) {
                Instruction.SND -> {
                    soundBuffer.add(valueOf(command.x))
                    return 1
                }
                Instruction.SET -> {
                    register[command.x.first()] = valueOf(command.y!!)
                    return 1
                }
                Instruction.ADD -> {
                    val prev = valueOf(command.x)
                    register[command.x.first()] = prev + valueOf(command.y!!)
                    return 1
                }
                Instruction.MUL -> {
                    val prev = valueOf(command.x)
                    register[command.x.first()] = prev * valueOf(command.y!!)
                    return 1
                }
                Instruction.MOD -> {
                    val prev = valueOf(command.x)
                    register[command.x.first()] = prev % valueOf(command.y!!)
                    return 1
                }
                Instruction.RCV -> {
                    if (valueOf(command.x) != 0L) {
                        recovered.add(soundBuffer.last())
                    }
                    return 1
                }
                Instruction.JGZ -> {
                    return if (valueOf(command.x) > 0L) {
                        valueOf(command.y!!).toInt()
                    } else {
                        1
                    }
                }

            }
        }


        data class Command(val instruction: Instruction, val x: String, val y: String? = null) {
            companion object {
                fun parseString(string: String): Command {
                    val split = string.split(" ")
                    if (split.size >= 2) {
                        return Command(Instruction.valueOf(split[0].toUpperCase()), split[1], split.getOrNull(2))
                    } else {
                        throw IllegalArgumentException()
                    }

                }
            }
        }

        enum class Instruction {
            SND,
            SET,
            ADD,
            MUL,
            MOD,
            RCV,
            JGZ
        }
    }
}
