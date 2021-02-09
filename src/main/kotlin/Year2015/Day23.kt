package Year2015

import Day

class Day23 : Day(2015,23) {
    val commands: List<Command> = input.asList.filterNotEmpty().map {
        Command(Instruction.valueOf(it.substring(0..2).toUpperCase()), it.substring(4).split(", "))
    }

    override fun part1(): String {
        val computer = Computer(commands)
        computer.runProgram()
        return computer.registers["b"].toString()
    }

    override fun part2(): String {
        val computer = Computer(commands)
        computer.registers["a"] = 1
        computer.runProgram()
        return computer.registers["b"].toString()
    }

    class Computer(val program: List<Command>) {
        val registers = mutableMapOf<String,Int>()

        fun runProgram() {
            var commandPointer = 0
            while (commandPointer in program.indices) {
                commandPointer = execute(commandPointer)
            }
        }

        private fun execute(index: Int): Int {
            val command = program[index]
            when (command.instruction) {
                Instruction.HLF -> {
                    registers[command.parameters.first()] = registers.getOrDefault(command.parameters.first(), 0) / 2
                    return index + 1
                }
                Instruction.TPL -> {
                    registers[command.parameters.first()] = registers.getOrDefault(command.parameters.first(), 0) * 3
                    return index + 1
                }
                Instruction.INC -> {
                    registers[command.parameters.first()] = registers.getOrDefault(command.parameters.first(), 0) + 1
                    return index + 1
                }
                Instruction.JMP -> {
                    return index + command.parameters.first().toInt()
                }
                Instruction.JIE -> {
                    if (registers.getOrDefault(command.parameters.first(),0) % 2 == 0) {
                        return index + command.parameters.last().toInt()
                    } else {
                        return index + 1
                    }
                }
                Instruction.JIO -> {
                    if (registers.getOrDefault(command.parameters.first(),0) == 1) {
                        return index + command.parameters.last().toInt()
                    } else {
                        return index + 1
                    }
                }
            }
        }
    }




    data class Command(val instruction: Instruction, val parameters: List<String>)

    enum class Instruction {
        HLF,
        TPL,
        INC,
        JMP,
        JIE,
        JIO
    }
}
